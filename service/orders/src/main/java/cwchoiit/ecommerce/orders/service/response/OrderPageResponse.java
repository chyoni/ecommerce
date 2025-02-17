package cwchoiit.ecommerce.orders.service.response;

import cwchoiit.ecommerce.orders.entity.Orders;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderPageResponse {
    private List<Orders> orders;
    private Long orderCount;

    public static OrderPageResponse from(List<Orders> orders, Long orderCount) {
        OrderPageResponse orderPageResponse = new OrderPageResponse();
        orderPageResponse.orders = orders;
        orderPageResponse.orderCount = orderCount;
        return orderPageResponse;
    }
}
