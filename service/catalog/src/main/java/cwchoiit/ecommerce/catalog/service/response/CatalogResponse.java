package cwchoiit.ecommerce.catalog.service.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogResponse {
    private Long productId;
    private String productName;
    private Long stock;
    private Long unitPrice;

    public static CatalogResponse from(Long productId, String productName, Long stock, Long unitPrice) {
        CatalogResponse response = new CatalogResponse();
        response.productId = productId;
        response.productName = productName;
        response.stock = stock;
        response.unitPrice = unitPrice;
        return response;
    }
}
