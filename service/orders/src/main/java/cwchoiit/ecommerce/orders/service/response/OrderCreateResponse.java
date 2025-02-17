package cwchoiit.ecommerce.orders.service.response;

import cwchoiit.ecommerce.orders.entity.Orders;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateResponse {
    private Long orderId;
    private Long productId;
    private Long userId;
    private Long quantity;
    private Long unitPrice;
    private Long totalPrice;

    public static OrderCreateResponse from(Orders order) {
        OrderCreateResponse orderCreateResponse = new OrderCreateResponse();
        orderCreateResponse.orderId = order.getOrderId();
        orderCreateResponse.productId = order.getProductId();
        orderCreateResponse.userId = order.getUserId();
        orderCreateResponse.quantity = order.getQuantity();
        orderCreateResponse.unitPrice = order.getUnitPrice();
        orderCreateResponse.totalPrice = order.getTotalPrice();
        return orderCreateResponse;
    }
}
