package co.uk.silverbars.order.repository;

import co.uk.silverbars.order.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
