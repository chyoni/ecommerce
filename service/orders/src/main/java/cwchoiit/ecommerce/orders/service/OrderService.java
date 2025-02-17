package cwchoiit.ecommerce.orders.service;

import cwchoiit.ecommerce.common.snowflake.Snowflake;
import cwchoiit.ecommerce.orders.entity.Orders;
import cwchoiit.ecommerce.orders.repository.OrdersRepository;
import cwchoiit.ecommerce.orders.service.request.OrderCreateRequest;
import cwchoiit.ecommerce.orders.service.response.OrderCreateResponse;
import cwchoiit.ecommerce.orders.service.response.OrderPageResponse;
import cwchoiit.ecommerce.orders.service.response.OrderResponse;
import cwchoiit.ecommerce.orders.utils.PageLimitCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final Snowflake snowflake = new Snowflake();
    private final OrdersRepository ordersRepository;

    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request) {
        Orders newOrder = ordersRepository.save(
                Orders.create(
                        snowflake.nextId(),
                        request.getProductId(),
                        request.getUserId(),
                        request.getQuantity(),
                        request.getUnitPrice()
                )
        );

        return OrderCreateResponse.from(newOrder);
    }

    public OrderPageResponse getOrdersByUserId(Long userId, Long page, Long pageSize) {
        List<Orders> orders = ordersRepository.findByUserId(userId, pageSize, (page - 1) * pageSize);

        return OrderPageResponse.from(
                orders,
                PageLimitCalculator.calculatePageLimit(page, pageSize, 10L)
        );
    }

    public OrderResponse getOrderByOrderId(Long orderId) {
        return ordersRepository.findByOrderId(orderId)
                .map(OrderResponse::from)
                .orElseThrow();
    }
}
