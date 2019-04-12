package co.uk.silverbars.order.service;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.domain.Order;
import co.uk.silverbars.order.dto.request.RequestDto;
import co.uk.silverbars.order.dto.response.ResponseDto;
import co.uk.silverbars.order.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    private OrderRepository repository;
    private OrderService service;

    @Before
    public void setUp() throws Exception {
        repository = mock(OrderRepository.class);
        service = new OrderServiceImpl(repository);
    }

    @Test
    public void givenOrder_whenCallAddOrder_thenSaveInMemoryDb() {
        RequestDto dto = RequestDto.builder().userId("user1")
                .price(100)
                .quantity(1.3)
                .orderType(OrderType.BUY).build();

        Order order = new Order();
        BeanUtils.copyProperties(dto, order);

        service.addOrder(dto);
        verify(repository).save(order);
    }

    @Test
    public void givenId_whenCallDeleteOrder_thenDeleteFromMemoryDb() {
        Order order = Order.builder()
                .id(1L)
                .orderType(OrderType.BUY)
                .price(100)
                .quantity(1.1)
                .userId("user1").build();

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(order));

        service.deleteOrder(1L);

        verify(repository).delete(order);
    }

    @Test
    public void givenOrderTypeIsSell_whenCallGetOrderSummary_thenReturnGroupByPriceAndNaturalSort() {
        Order order1 = Order.builder()
                .id(1L)
                .orderType(OrderType.SELL)
                .price(100)
                .quantity(1.1)
                .userId("user1").build();
        Order order2 = Order.builder()
                .id(2L)
                .orderType(OrderType.SELL)
                .price(80)
                .quantity(5.1)
                .userId("user1").build();
        Order order3 = Order.builder()
                .id(2L)
                .orderType(OrderType.SELL)
                .price(80)
                .quantity(2.9)
                .userId("user1").build();
        Order order4 = Order.builder()
                .id(2L)
                .orderType(OrderType.BUY)
                .price(80)
                .quantity(2.9)
                .userId("user1").build();

        when(repository.findAll()).thenReturn(Arrays.asList(order1,order2,order3,order4));

        List<ResponseDto> actual =  service.getOrderSummary(OrderType.SELL);

        assertTrue(actual.size() == 2);
        assertTrue(actual.get(0).price < actual.get(1).price); //Lowest prices are displayed first
        assertTrue(actual.get(0).totalQuantity == 8);  //the same price should be merged together
        assertTrue(actual.get(1).totalQuantity == 1.1);
    }

    @Test
    public void givenOrderTypeIsBuy_whenCallGetOrderSummary_thenReturnGroupByPriceAndReversedSort() {
        Order order1 = Order.builder()
                .id(1L)
                .orderType(OrderType.BUY)
                .price(100)
                .quantity(1.1)
                .userId("user1").build();
        Order order2 = Order.builder()
                .id(2L)
                .orderType(OrderType.BUY)
                .price(80)
                .quantity(5.1)
                .userId("user1").build();
        Order order3 = Order.builder()
                .id(2L)
                .orderType(OrderType.BUY)
                .price(80)
                .quantity(2.9)
                .userId("user1").build();
        Order order4 = Order.builder()
                .id(2L)
                .orderType(OrderType.SELL)
                .price(80)
                .quantity(2.9)
                .userId("user1").build();

        when(repository.findAll()).thenReturn(Arrays.asList(order1,order2,order3,order4));

        List<ResponseDto> actual =  service.getOrderSummary(OrderType.BUY);

        assertTrue(actual.size() == 2);
        assertTrue(actual.get(0).price > actual.get(1).price); //Lowest prices are displayed first
        assertTrue(actual.get(1).totalQuantity == 8);  //the same price should be merged together
        assertTrue(actual.get(0).totalQuantity == 1.1);
    }
}