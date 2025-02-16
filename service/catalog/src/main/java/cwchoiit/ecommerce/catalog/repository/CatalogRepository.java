package cwchoiit.ecommerce.catalog.repository;

import cwchoiit.ecommerce.catalog.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findByProductId(Long productId);
}
