package cwchoiit.ecommerce.catalog.service.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogCreateRequest {
    private String productName;
    private Long stock;
    private Long unitPrice;

    public static CatalogCreateRequest from(String productName, Long stock, Long unitPrice) {
        CatalogCreateRequest catalogCreateRequest = new CatalogCreateRequest();
        catalogCreateRequest.productName = productName;
        catalogCreateRequest.stock = stock;
        catalogCreateRequest.unitPrice = unitPrice;

        return catalogCreateRequest;
    }
}
