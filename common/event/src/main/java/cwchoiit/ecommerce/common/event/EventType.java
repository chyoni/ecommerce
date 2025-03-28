package cwchoiit.ecommerce.common.event;

import cwchoiit.ecommerce.common.event.payload.EventPayload;
import cwchoiit.ecommerce.common.event.payload.OrderCreatedEventPayload;
import cwchoiit.ecommerce.common.event.payload.OrderDeletedEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ORDER_CREATED(OrderCreatedEventPayload.class, Topic.ECOMMERCE_ORDER),
    ORDER_DELETED(OrderDeletedEventPayload.class, Topic.ECOMMERCE_ORDER);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[from:22] type = {}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String ECOMMERCE_ORDER = "ecommerce-order";
    }
}
