package cwchoiit.ecommerce.catalog.consumer;

import cwchoiit.ecommerce.catalog.service.CatalogService;
import cwchoiit.ecommerce.common.event.Event;
import cwchoiit.ecommerce.common.event.payload.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static cwchoiit.ecommerce.common.event.EventType.Topic.ECOMMERCE_ORDER;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogKafkaConsumer {
    private final CatalogService catalogService;

    @KafkaListener(
            topics = { ECOMMERCE_ORDER },
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(String message, Acknowledgment ack) {
        log.info("[listen:21] message: {}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            catalogService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
