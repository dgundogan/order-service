package co.uk.silverbars.order.controller;

import co.uk.silverbars.order.constant.OrderType;
import co.uk.silverbars.order.dto.request.RequestDto;
import co.uk.silverbars.order.dto.response.ResponseDto;
import co.uk.silverbars.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping(value = "/")
    public ResponseEntity addOrder(RequestDto request){
        log.info("Creating Order: {}",request);
        service.addOrder(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrder(@PathVariable("id") long id){
        log.info("Fetching & Deleting Order with id {}", id);
        service.deleteOrder(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseDto>> getSummaryOrders(
            @RequestParam(value = "orderType", required = true) OrderType orderType){
        return ResponseEntity.ok(service.getOrderSummary(orderType));
    }
}
