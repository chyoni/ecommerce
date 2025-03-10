package cwchoiit.ecommerce.common.outboxmessagerelay;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MessageRelayCoordinator {
    private final StringRedisTemplate redisTemplate;

    /**
     * 이 모듈을 다운받고 실행한 서비스의 이름
     */
    @Value("${spring.application.name}")
    private String applicationName;
    /**
     * 이 모듈을 다운받고 실행한 서비스의 고유 ID
     */
    @Value("${eureka.instance.instance-id}")
    private String appId;
    /**
     * 3초마다 ping 날림
     */
    private final int PING_INTERNAL_SECONDS = 3;
    /**
     * 핑 날리는 것을 3번 시도해도 실패한 경우, 애플리케이션이 죽었다고 판단
     */
    private final int PING_FAILURE_THRESHOLD = 3;

    public AssignedShard assignShards() {
        return AssignedShard.of(appId, findAppIds(), MessageRelayConstants.SHARD_COUNT);
    }

    /**
     * Redis zSet 에 저장된 특정 서비스의 모든 살아있는 Application 을 리스트로 반환
     * redisTemplate.opsForZSet().reverseRange(generateKey(), 0, -1) 이렇게 하면,
     * Sorted Set 에 들어있는 generateKey()에 해당하는 모든 값들을 다 찾는 것. (0, -1)이 모든 범위를 말함.
     *
     * @return 이 모듈을 내려받고 실행한 특정 서비스의 모든 Application 의 리스트
     */
    private List<String> findAppIds() {
        return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange(generateKey(), 0, -1))
                .stream()
                .sorted()
                .toList();
    }

    @Scheduled(fixedDelay = PING_INTERNAL_SECONDS, timeUnit = TimeUnit.SECONDS)
    public void ping() {
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection conn = (StringRedisConnection) action;
            String key = generateKey();

            // 3초마다 ping 을 날려서, 이 appId의 score 를 현재 시간으로 변경
            conn.zAdd(key, Instant.now().toEpochMilli(), appId);

            // score 가 9초가 지난 애플리케이션은 zSet 에서 삭제
            conn.zRemRangeByScore(
                    key,
                    Double.NEGATIVE_INFINITY,
                    Instant.now().minusSeconds(PING_INTERNAL_SECONDS * PING_FAILURE_THRESHOLD).toEpochMilli()
            );
            return null;
        });
    }

    /**
     * 이 애플리케이션이 내려갈 때, 본인 스스로를 Redis zSet 에서 제거
     */
    @PreDestroy
    public void shutdown() {
        redisTemplate.opsForZSet().remove(generateKey(), appId);
    }

    private String generateKey() {
        return "message-relay-coordinator::app-list::%s".formatted(applicationName);
    }
}
