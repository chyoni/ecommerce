package cwchoiit.ecommerce.common.outboxmessagerelay;

import cwchoiit.ecommerce.common.outboxmessagerelay.entity.Outbox;
import cwchoiit.ecommerce.common.outboxmessagerelay.entity.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageRelay {
    private final OutboxRepository outboxRepository;
    private final MessageRelayCoordinator messageRelayCoordinator;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 트랜잭션 커밋 직전에 이벤트를 받는다. 이벤트를 받으면, Outbox 테이블에 이 이벤트를 저장해둔다.
     * @param outboxEvent 이벤트
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createOutbox(OutboxEvent outboxEvent) {
        log.info("[createOutbox:22] outBoxEvent : {}", outboxEvent);
        outboxRepository.save(outboxEvent.getOutbox());
    }

    /**
     * 트랜잭션 커밋 직후 이벤트를 받는다. 이벤트를 받으면, 미리 선언해 둔
     * {@code messageRelayPublishEventExecutor} 스레드 풀을 사용해서 비동기 적으로 이벤트를
     * Kafka 에 전송한다.
     * @param outboxEvent 이벤트
     */
    @Async("messageRelayPublishEventExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishEvent(OutboxEvent outboxEvent) {
        publishEvent(outboxEvent.getOutbox());
    }

    private void publishEvent(Outbox outbox) {
        try {
            kafkaTemplate.send(
                    outbox.getEventType().getTopic(),
                    String.valueOf(outbox.getShardKey()),
                    outbox.getPayload()
            ).get(1, TimeUnit.SECONDS); // get()하면 결과를 번환받을 수 있음

            // 정상적으로 전송이 됐다면 이 라인을 실행한다. 정상 전송이 됐으면 나의 비즈니스 정책의 경우 Outbox 레코드를 테이블에서 삭제한다.
            outboxRepository.delete(outbox);
        } catch (Exception e) {
            log.error("[publishEvent:54] outbox = {}", outbox, e);
        }
    }

    /**
     * 10초가 지나도 이벤트가 전송되지 않은 것들을 전부 Polling 해서 Kafka 로 전송해주는 스케쥴러
     */
    @Scheduled(
            fixedDelay = 10,
            initialDelay = 5,
            timeUnit = TimeUnit.SECONDS,
            scheduler = "messageRelayPublishPendingEventExecutor"
    )
    public void publishPendingEvents() {
        // 이 모듈을 실행하고 있는 특정 서비스의 특정 애플리케이션(예: 오더 서비스의 1번 애플리케이션)이 담당하는 샤드들을 조회
        AssignedShard assignedShard = messageRelayCoordinator.assignShards();
        log.debug("[publishPendingEvents:69] assignedShard = {}", assignedShard.getShards());

        for (Long shard : assignedShard.getShards()) {
            List<Outbox> outboxes = outboxRepository.findAllByShardKeyAndCreatedAtLessThanEqualOrderByCreatedAtAsc(
                    shard,
                    LocalDateTime.now().minusSeconds(10), // 현재 시간보다 10초가 지난
                    Pageable.ofSize(100) // 모든것들을 조회하진 말고 100개씩
            );
            for (Outbox outbox : outboxes) {
                publishEvent(outbox);
            }
        }
    }
}
