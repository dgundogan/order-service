package co.uk.silverbars.order.service;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.dto.request.RequestDto;
import co.uk.silverbars.order.dto.response.ResponseDto;

import java.util.List;

public interface OrderService {
    void addOrder(RequestDto orderDto);
    void deleteOrder(long orderId);
    List<ResponseDto> getOrderSummary(OrderType orderType);
}
