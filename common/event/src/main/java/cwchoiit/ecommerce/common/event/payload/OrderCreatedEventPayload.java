package cwchoiit.ecommerce.common.event.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEventPayload implements EventPayload {
    private Long orderId;
    private Long productId;
    private Long userId;
    private Long quantity;
    private Long unitPrice;
    private Long totalPrice;
}
