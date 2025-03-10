package cwchoiit.ecommerce.common.event;

import cwchoiit.ecommerce.common.event.payload.EventPayload;
import cwchoiit.ecommerce.common.event.payload.OrderCreatedEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static cwchoiit.ecommerce.common.event.EventType.ORDER_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class EventTest {

    @Test
    void toJsonFromJson() {
        OrderCreatedEventPayload orderCreatedEventPayload = OrderCreatedEventPayload.builder()
                .orderId(1L)
                .userId(1L)
                .productId(1L)
                .quantity(10L)
                .unitPrice(1000L)
                .totalPrice(10000L)
                .build();

        // OrderCreate Event 생성
        Event<EventPayload> event = Event.of(1L, ORDER_CREATED, orderCreatedEventPayload);

        // Event 를 Json 으로
        String eventJson = event.toJson();
        // Json 으로 변환된 이벤트는 Not Null
        assertThat(eventJson).isNotNull();
        // Json Event 데이터를 다시 Event 로 변환
        Event<EventPayload> eventFromJson = Event.fromJson(eventJson);

        // Json 에서 Event 인스턴스로 변환된 데이터 검증
        assertThat(eventFromJson).isNotNull();
        assertThat(eventFromJson.getEventId()).isEqualTo(event.getEventId());
        assertThat(eventFromJson.getEventType()).isEqualTo(event.getEventType());
        assertThat(eventFromJson.getEventPayload().getClass().getName())
                .isEqualTo(event.getEventPayload().getClass().getName());

        // 변환된 Event 인스턴스의 EventPayload 타입은 OrderCreate
        assertThat(eventFromJson.getEventPayload()).isInstanceOf(OrderCreatedEventPayload.class);

        OrderCreatedEventPayload eventPayload = (OrderCreatedEventPayload) eventFromJson.getEventPayload();

        assertThat(eventPayload.getOrderId()).isEqualTo(orderCreatedEventPayload.getOrderId());
        assertThat(eventPayload.getUserId()).isEqualTo(orderCreatedEventPayload.getUserId());
        assertThat(eventPayload.getProductId()).isEqualTo(orderCreatedEventPayload.getProductId());
        assertThat(eventPayload.getQuantity()).isEqualTo(orderCreatedEventPayload.getQuantity());
        assertThat(eventPayload.getUnitPrice()).isEqualTo(orderCreatedEventPayload.getUnitPrice());
        assertThat(eventPayload.getTotalPrice()).isEqualTo(orderCreatedEventPayload.getTotalPrice());
    }
}