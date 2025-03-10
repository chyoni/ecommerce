package cwchoiit.ecommerce.common.outboxmessagerelay.entity;

import cwchoiit.ecommerce.common.event.EventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@Table(name = "outbox")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox {
    @Id
    private Long outboxId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String payload;

    /**
     * 이 테이블의 shardKey 가 의미하는 것은, 이 outbox-message-relay 모듈을 내려받고 사용하는 Producer 애플리케이션들이 담당하는 샤드의 번호를 나타낸다.
     * 예를 들어, 오더 서비스의 데이터베이스에 물리적 샤드가 4개가 있고, 오더 서비스 애플리케이션이 2개 띄워져 있을때, 하나는 0,1번 샤드를 담당하고 하나는 2,3번 샤드를 담당할 것이다.
     * 그럼 0,1번 또는 2,3번이 이 shardKey 값에 들어간다. 그래서 이 샤드키 값을 조건으로 Outbox 테이블에서 데이터를 가져와야 동일한 파티션에 넘겨야 하는 레코드들을 찾아낼 수 있기 때문이다.
     * 그리고 이 shardKey 는 전송되지 않은 이벤트들을 polling 해서 전부 전송할때 사용되고, {@code publishEvent}에서도 사용된다. 같은 파티션에 이벤트를 전달한것들은 순서가 보장되기 때문이다.
     */
    private Long shardKey;
    private LocalDateTime createdAt;

    public static Outbox create(Long outboxId, EventType eventType, String payload, Long shardKey) {
        Outbox outbox = new Outbox();
        outbox.outboxId = outboxId;
        outbox.eventType = eventType;
        outbox.payload = payload;
        outbox.shardKey = shardKey;
        outbox.createdAt = LocalDateTime.now();
        return outbox;
    }
}
