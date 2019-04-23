package co.uk.silverbars.order.repository;

import co.uk.silverbars.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
