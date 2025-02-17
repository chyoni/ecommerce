package cwchoiit.ecommerce.orders.repository;

import cwchoiit.ecommerce.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderId(Long orderId);

    @Query(
            value = "select orders.order_id, orders.product_id, orders.user_id, orders.quantity," +
                    " orders.unit_price, orders.total_price, orders.created_at, orders.modified_at " +
                    "from (" +
                    "   select order_id " +
                    "   from orders " +
                    "   where user_id = :userId " +
                    "   order by order_id desc " +
                    "   limit :limit offset :offset " +
                    ") t left join orders on t.order_id = orders.order_id",
            nativeQuery = true
    )
    List<Orders> findByUserId(@Param("userId") Long userId, @Param("limit") Long limit, @Param("offset") Long offset);

    @Query(
            value = "select count(*) " +
                    "from (" +
                    "   select order_id " +
                    "   from orders " +
                    "   where user_id = :userId " +
                    "   limit :limit " +
                    ") t",
            nativeQuery = true
    )
    long count(@Param("userId") Long userId, @Param("limit") Long limit);
}
