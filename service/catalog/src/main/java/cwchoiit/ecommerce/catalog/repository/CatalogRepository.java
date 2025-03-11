package cwchoiit.ecommerce.catalog.repository;

import cwchoiit.ecommerce.catalog.entity.Catalog;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Optional<Catalog> findByProductId(Long productId);
}
