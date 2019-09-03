package co.uk.silverbars.order.service;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.domain.Order;
import co.uk.silverbars.order.dto.request.RequestDto;
import co.uk.silverbars.order.dto.response.ResponseDto;
import co.uk.silverbars.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addOrder(RequestDto request){
        Order order = new Order();
        BeanUtils.copyProperties(request, order);
        this.repository.save(order);
    }

    @Override
    public void deleteOrder(long id) {
        final Optional<Order> order = this.repository.findById(id);
        order.ifPresent(this.repository::delete);
    }

    @Override
    public List<ResponseDto> getOrderSummary(OrderType orderType) {

        Stream<ResponseDto>  result = this.repository.findAll().stream()
                .filter(item -> item.getOrderType().equals(orderType))
                .collect(Collectors.groupingBy(Order::getPrice,Collectors.summingDouble(Order::getQuantity)))
                .entrySet().stream()
                .map(it -> ResponseDto.builder().price(it.getKey()).totalQuantity(it.getValue()).build());

        if(OrderType.SELL.equals(orderType)){
            return result
                    .sorted(Comparator.comparingDouble(ResponseDto::getPrice))
                    .collect(Collectors.toList());
        } else {
            return result
                    .sorted(Comparator.comparingDouble(ResponseDto::getPrice).reversed())
                    .collect(Collectors.toList());
        }
    }
    
    /*
        @Override
    public Map<Long, Double> getOrderSummary(OrderType orderType) {
        if(OrderType.SELL.equals(orderType)){
            return this.repository.findAll().stream()
                    .filter(item -> item.getOrderType().equals(orderType))
                    .sorted(comparingDouble(Order::getPrice))
                    .collect(groupingBy(Order::getPrice, summingDouble(Order::getQuantity)));
        } else {
            return this.repository.findAll().stream()
                    .filter(item -> item.getOrderType().equals(orderType))
                    .sorted(comparingDouble(Order::getPrice).reversed())
                    .collect(groupingBy(Order::getPrice, summingDouble(Order::getQuantity)));
        }
    }
     */
}
