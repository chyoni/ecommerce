package cwchoiit.ecommerce.catalog.service.eventhandler;

import cwchoiit.ecommerce.catalog.entity.Catalog;
import cwchoiit.ecommerce.catalog.repository.CatalogRepository;
import cwchoiit.ecommerce.common.event.Event;
import cwchoiit.ecommerce.common.event.payload.OrderCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static cwchoiit.ecommerce.common.event.EventType.ORDER_CREATED;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateEventHandler implements EventHandler<OrderCreatedEventPayload> {

    private final CatalogRepository catalogRepository;

    @Override
    public void handle(Event<OrderCreatedEventPayload> event) {
        log.info("[handle:22] event type: {}", event.getEventType());
        OrderCreatedEventPayload payload = event.getEventPayload();

        Catalog catalog = catalogRepository.findByProductId(payload.getProductId()).orElseThrow();
        catalog.decreaseStock(payload.getQuantity());
    }

    @Override
    public boolean supports(Event<OrderCreatedEventPayload> event) {
        return ORDER_CREATED == event.getEventType();
    }
}
