package cwchoiit.ecommerce.common.outboxmessagerelay.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    List<Outbox> findAllByShardKeyAndCreatedAtLessThanEqualOrderByCreatedAtAsc(Long shardKey,
                                                                               LocalDateTime from,
                                                                               Pageable pageable);
}
