package cwchoiit.ecommerce.catalog.service;

import cwchoiit.ecommerce.catalog.entity.Catalog;
import cwchoiit.ecommerce.catalog.repository.CatalogRepository;
import cwchoiit.ecommerce.catalog.service.eventhandler.EventHandler;
import cwchoiit.ecommerce.catalog.service.request.CatalogCreateRequest;
import cwchoiit.ecommerce.catalog.service.response.CatalogCreateResponse;
import cwchoiit.ecommerce.catalog.service.response.CatalogResponse;
import cwchoiit.ecommerce.common.event.Event;
import cwchoiit.ecommerce.common.event.payload.EventPayload;
import cwchoiit.ecommerce.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogService {
    private final CatalogRepository catalogRepository;
    private final Snowflake snowflake = new Snowflake();
    private final List<EventHandler<? extends EventPayload>> eventHandlers;

    @SuppressWarnings("unchecked")
    public <T extends EventPayload> void handleEvent(Event<T> event) {
        for (EventHandler<? extends EventPayload> eventHandler : eventHandlers) {
            EventHandler<T> handler = (EventHandler<T>) eventHandler;
            if (handler.supports(event)) {
                handler.handle(event);
            }
        }
    }

    public List<CatalogResponse> getAllCatalogs() {
        return catalogRepository.findAll().stream()
                .map(catalog ->
                        CatalogResponse.from(
                                catalog.getProductId(),
                                catalog.getProductName(),
                                catalog.getStock(),
                                catalog.getUnitPrice()
                        )
                )
                .toList();
    }

    @Transactional
    public CatalogCreateResponse createCatalog(CatalogCreateRequest request) {
        Catalog createdCatalog = catalogRepository.save(
                Catalog.from(
                        snowflake.nextId(),
                        request.getProductName(),
                        request.getStock(),
                        request.getUnitPrice()
                )
        );
        return CatalogCreateResponse.from(createdCatalog);
    }
}
