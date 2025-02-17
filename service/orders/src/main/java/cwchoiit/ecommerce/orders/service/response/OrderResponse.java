package cwchoiit.ecommerce.orders.service.response;

import cwchoiit.ecommerce.orders.entity.Orders;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private Long userId;
    private Long quantity;
    private Long unitPrice;
    private Long totalPrice;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static OrderResponse from(Orders order) {
        OrderResponse response = new OrderResponse();
        response.orderId = order.getOrderId();
        response.productId = order.getProductId();
        response.userId = order.getUserId();
        response.quantity = order.getQuantity();
        response.unitPrice = order.getUnitPrice();
        response.totalPrice = order.getTotalPrice();
        response.createdAt = order.getCreatedAt();
        response.modifiedAt = order.getModifiedAt();
        return response;
    }
}
