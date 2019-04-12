package co.uk.silverbars.order.repository;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository repository;
    private Order order;

    @Before
    public void setup() {
        order =  Order.builder()
                        .id(1L)
                        .userId("user1")
                        .quantity(1)
                        .price(100L)
                        .orderType(OrderType.BUY).build();
    }

    @Test
    public void whenFindingOrderById_thenCorrect() {
        repository.save(order);
        assertThat(repository.findById(1L)).isInstanceOf(Optional.class);
    }

    @Test
    public void whenFindingAllOrder_thenCorrect() {
        repository.save(order);
        assertThat(repository.findAll()).isInstanceOf(List.class);
    }
}