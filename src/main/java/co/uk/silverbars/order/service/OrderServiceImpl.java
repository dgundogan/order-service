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
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addOrder(RequestDto request){
        //Transform from dto to entity object
        Order order = new Order();
        BeanUtils.copyProperties(request, order);
        //Other alternative is object mapper but I prefer copy objecct for performance
        //private  ObjectMapper objectMapper;
        //objectMapper = new ObjectMapper();
        //Order order = objectMapper.readValue(objectMapper.writeValueAsString(request),Order.class);
        //Register an order
        this.repository.save(order);
    }

    @Override
    public void deleteOrder(long id) {
        //check whether data exists or not
        final Optional<Order> order = this.repository.findById(id);
        //if data exists , delete it.
        order.ifPresent(this.repository::delete);
    }

    @Override
    public List<ResponseDto> getOrderSummary(OrderType orderType) {
        //the same price should be merged together : using Group By Price and sum quantity - Stream operation
        Stream<ResponseDto>  result = StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .filter(item -> item.getOrderType().equals(orderType))
                .collect(Collectors.groupingBy(Order::getPrice,Collectors.summingDouble(Order::getQuantity)))
                .entrySet().stream()
                .map(it -> ResponseDto.builder().price(it.getKey()).totalQuantity(it.getValue()).build());
        //if orderType is SELL, it returns natural sorted bu price
        //else orderType is BUY, it returns reversed order by price
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
}
