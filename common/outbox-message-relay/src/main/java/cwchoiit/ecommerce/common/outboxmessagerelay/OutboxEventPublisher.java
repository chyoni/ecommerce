package cwchoiit.ecommerce.common.outboxmessagerelay;

import cwchoiit.ecommerce.common.event.Event;
import cwchoiit.ecommerce.common.event.EventType;
import cwchoiit.ecommerce.common.event.payload.EventPayload;
import cwchoiit.ecommerce.common.outboxmessagerelay.entity.Outbox;
import cwchoiit.ecommerce.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final Snowflake outboxIdSnowflake = new Snowflake();
    private final Snowflake eventIdSnowflake = new Snowflake();
    private final ApplicationEventPublisher eventPublisher;

    public void publish(EventType eventType, EventPayload eventPayload, Long shardKey) {
        // 물리적 샤드 개수 MessageRelayConstants.SHARD_COUNT (4)
        // 주문 서비스의 경우 orderId == shard Key
        // shardKey % MessageRelayConstants.SHARD_COUNT 의 결과가 저장될 샤드 번호
        Outbox outbox = Outbox.create(
                outboxIdSnowflake.nextId(),
                eventType,
                Event.of(eventIdSnowflake.nextId(), eventType, eventPayload).toJson(),
                shardKey % MessageRelayConstants.SHARD_COUNT
        );

        // MessageRelay 에서 이 이벤트르 받게된다. 스프링의 ApplicationEventPublisher 를 통해 이벤트를 발행
        // 이 이벤트를 발행하면 곧바로 MessageRelay 에서 이벤트 리스너가 받는건 아니고, 시점에 맞게 호출된다.
        // 나의 경우, 커밋 바로 직전과 직후로 설정
        eventPublisher.publishEvent(OutboxEvent.create(outbox));
    }
}
