package cwchoiit.ecommerce.catalog.service.response;

import cwchoiit.ecommerce.catalog.entity.Catalog;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogCreateResponse {
    private Long productId;
    private String productName;
    private Long stock;
    private Long unitPrice;

    public static CatalogCreateResponse from(Catalog catalog) {
        CatalogCreateResponse response = new CatalogCreateResponse();
        response.productId = catalog.getProductId();
        response.productName = catalog.getProductName();
        response.stock = catalog.getStock();
        response.unitPrice = catalog.getUnitPrice();
        return response;
    }
}
