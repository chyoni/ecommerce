package cwchoiit.ecommerce.user.client;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderServiceClient {

    private RestClient restClient;

    @Value("${endpoints.order-service}")
    private String orderServiceUrl;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(orderServiceUrl);
    }

    public UserOrdersPreviewResponse getOrdersPreview(Long userId) {
        return restClient.get()
                .uri("/{userId}?page=1&pageSize=3", userId)
                .retrieve()
                .body(UserOrdersPreviewResponse.class);
    }

    @Getter
    @ToString
    public static class UserOrdersPreviewResponse {
        private List<Order> orders;
        private Long orderCount;
    }

    @Getter
    @ToString
    public static class Order {
        private Long orderId;
        private Long productId;
        private Long userId;
        private Long quantity;
        private Long unitPrice;
        private Long totalPrice;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
