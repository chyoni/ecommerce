package cwchoiit.ecommerce.catalog.controller;

import cwchoiit.ecommerce.catalog.service.CatalogService;
import cwchoiit.ecommerce.catalog.service.request.CatalogCreateRequest;
import cwchoiit.ecommerce.catalog.service.response.CatalogCreateResponse;
import cwchoiit.ecommerce.catalog.service.response.CatalogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/catalogs")
public class CatalogController {
    private final Environment environment;
    private final CatalogService catalogService;

    @GetMapping("/healthz")
    public String healthCheck() {
        log.info("[healthCheck:17] server port : {}", environment.getProperty("local.server.port"));
        return "Health Good";
    }

    @GetMapping
    public ResponseEntity<List<CatalogResponse>> getCatalogs() {
        return ResponseEntity.ok(catalogService.getAllCatalogs());
    }

    @PostMapping
    public ResponseEntity<CatalogCreateResponse> createCatalog(@RequestBody CatalogCreateRequest request) {
        CatalogCreateResponse newCatalog = catalogService.createCatalog(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCatalog);
    }
}
