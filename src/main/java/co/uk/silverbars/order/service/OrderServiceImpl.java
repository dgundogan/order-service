package co.uk.silverbars.order.service;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.domain.Order;
import co.uk.silverbars.order.dto.request.RequestDto;
import co.uk.silverbars.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

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
}
