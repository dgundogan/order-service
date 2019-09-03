package co.uk.silverbars.order.service;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.dto.request.RequestDto;

import java.util.Map;

public interface OrderService {
    void addOrder(RequestDto orderDto);
    void deleteOrder(long orderId);
    Map<Long, Double> getOrderSummary(OrderType orderType);
}
