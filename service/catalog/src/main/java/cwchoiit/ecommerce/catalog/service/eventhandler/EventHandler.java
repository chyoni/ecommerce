package cwchoiit.ecommerce.catalog.service.eventhandler;

import cwchoiit.ecommerce.common.event.Event;
import cwchoiit.ecommerce.common.event.payload.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
}
