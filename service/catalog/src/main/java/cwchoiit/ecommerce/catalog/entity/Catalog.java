package cwchoiit.ecommerce.catalog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @Column(nullable = false, length = 120, unique = true)
    private Long productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Long stock;
    @Column(nullable = false)
    private Long unitPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Catalog from(Long productId, String productName, Long stock, Long unitPrice) {
        Catalog catalog = new Catalog();
        catalog.productId = productId;
        catalog.productName = productName;
        catalog.stock = stock;
        catalog.unitPrice = unitPrice;
        catalog.createdAt = LocalDateTime.now();
        catalog.modifiedAt = LocalDateTime.now();

        return catalog;
    }
}
