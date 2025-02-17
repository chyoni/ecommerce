package cwchoiit.ecommerce.orders.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
    @Id
    private Long orderId;
    private Long productId;
    private Long userId; // shard key
    private Long quantity;
    private Long unitPrice;
    private Long totalPrice;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Orders create(Long orderId,
                                Long productId,
                                Long userId,
                                Long quantity,
                                Long unitPrice) {
        Orders orders = new Orders();
        orders.orderId = orderId;
        orders.productId = productId;
        orders.userId = userId;
        orders.quantity = quantity;
        orders.unitPrice = unitPrice;
        orders.totalPrice = quantity * unitPrice;
        orders.createdAt = LocalDateTime.now();
        orders.modifiedAt = LocalDateTime.now();
        return orders;
    }
}
