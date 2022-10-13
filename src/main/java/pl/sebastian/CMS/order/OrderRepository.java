package pl.sebastian.CMS.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value="SELECT COUNT(o) FROM orders o WHERE o.status = :#{#orderStatus?.name()}", nativeQuery = true)
    Integer countOrdersByStatus(@Param("orderStatus") Order.OrderStatus orderStatusEnum);

    @Query(value="SELECT * FROM orders o WHERE o.status = :#{#orderStatus?.name()}", nativeQuery = true)
    List<Order> getOrdersByStatus(@Param("orderStatus") Order.OrderStatus orderStatusEnum);

}
