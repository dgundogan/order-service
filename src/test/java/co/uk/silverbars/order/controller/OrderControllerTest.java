package co.uk.silverbars.order.controller;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.dto.request.RequestDto;
import co.uk.silverbars.order.dto.response.ResponseDto;
import co.uk.silverbars.order.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    private OrderController orderController;
    private OrderService service;

    @Before
    public void setUp() {
        service = mock(OrderService.class);
        orderController = new OrderController(service);
    }

    @Test
    public void givenDto_whenCallAddOrder_thenReturnsOK() {
        RequestDto request = RequestDto.builder().userId("user1")
                .price(100)
                .quantity(1.3)
                .orderType(OrderType.BUY).build();

        orderController.addOrder(request);

        verify(service).addOrder(request);
        assertTrue(orderController.addOrder(request).equals(ResponseEntity.ok().build()));
    }

    @Test
    public void givenId_whenCallRemoveOrder_thenReturnsNoContent() {

        orderController.removeOrder(1);

        verify(service).deleteOrder(1);
    }

    @Test
    public void givenOrderTypeIsSELL_whenCallGetSummaryOrders_thenReturnsAllSellRecords() {
        ResponseDto response = ResponseDto.builder()
                .price(100)
                .totalQuantity(3.4)
                .build();
        //mocking
        when(service.getOrderSummary(OrderType.SELL)).thenReturn(Collections.singletonList(response));

        ResponseEntity<List<ResponseDto>> actual = orderController.getSummaryOrders(OrderType.SELL);
        //assert
        assertTrue(actual.getBody().size() > 0);
        assertTrue(actual.getStatusCode().equals(HttpStatus.OK));
        assertTrue(actual.getBody().get(0).equals(response));

    }
}