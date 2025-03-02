package cwchoiit.ecommerce.orders.controller;

import cwchoiit.ecommerce.orders.service.OrderService;
import cwchoiit.ecommerce.orders.service.request.OrderCreateRequest;
import cwchoiit.ecommerce.orders.service.response.OrderCreateResponse;
import cwchoiit.ecommerce.orders.service.response.OrderPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final Environment environment;
    private final OrderService orderService;

    @GetMapping("/healthz")
    public String status() {
        log.info("[healthCheck:21] server port : {}", environment.getProperty("local.server.port"));
        return "Health Good";
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderCreateResponse> create(@PathVariable("userId") Long userId,
                                                      @RequestBody OrderCreateRequest request) {
        OrderCreateResponse createdOrder = orderService.createOrder(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<OrderPageResponse> readAll(@PathVariable("userId") Long userId,
                                                     @RequestParam("page") Long page,
                                                     @RequestParam("pageSize") Long pageSize) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId, page, pageSize));
    }


}
