package cwchoiit.ecommerce.common.outboxmessagerelay;

import cwchoiit.ecommerce.common.outboxmessagerelay.entity.Outbox;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OutboxEvent {
    private Outbox outbox;

    public static OutboxEvent create(Outbox outbox) {
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.outbox = outbox;
        return outboxEvent;
    }
}
