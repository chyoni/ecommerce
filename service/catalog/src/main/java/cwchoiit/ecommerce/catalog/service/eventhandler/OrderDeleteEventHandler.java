package cwchoiit.ecommerce.catalog.service.eventhandler;

import cwchoiit.ecommerce.catalog.entity.Catalog;
import cwchoiit.ecommerce.catalog.repository.CatalogRepository;
import cwchoiit.ecommerce.common.event.Event;
import cwchoiit.ecommerce.common.event.payload.OrderCreatedEventPayload;
import cwchoiit.ecommerce.common.event.payload.OrderDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static cwchoiit.ecommerce.common.event.EventType.ORDER_CREATED;
import static cwchoiit.ecommerce.common.event.EventType.ORDER_DELETED;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDeleteEventHandler implements EventHandler<OrderDeletedEventPayload> {

    private final CatalogRepository catalogRepository;

    @Override
    public void handle(Event<OrderDeletedEventPayload> event) {
        log.info("[handle:22] event type : {}", event.getEventType());
        OrderDeletedEventPayload payload = event.getEventPayload();

        Catalog catalog = catalogRepository.findByProductId(payload.getProductId()).orElseThrow();
        catalog.increaseStock(payload.getQuantity());
    }

    @Override
    public boolean supports(Event<OrderDeletedEventPayload> event) {
        return ORDER_DELETED == event.getEventType();
    }
}
