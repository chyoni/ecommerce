package cwchoiit.ecommerce.orders.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateRequest {
    private Long productId;
    private Long userId;
    private Long quantity;
    private Long unitPrice;

    public static OrderCreateRequest from(Long productId, Long userId, Long quantity, Long unitPrice) {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.productId = productId;
        orderCreateRequest.userId = userId;
        orderCreateRequest.quantity = quantity;
        orderCreateRequest.unitPrice = unitPrice;
        return orderCreateRequest;
    }
}
