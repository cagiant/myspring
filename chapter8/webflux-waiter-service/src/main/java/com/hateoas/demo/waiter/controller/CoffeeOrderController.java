package com.hateoas.demo.waiter.controller;

import com.hateoas.demo.waiter.controller.request.NewOrderRequest;
import com.hateoas.demo.waiter.model.CoffeeOrder;
import com.hateoas.demo.waiter.service.CoffeeService;
import com.hateoas.demo.waiter.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author guokaiqiang
 * @date 2020/6/18 22:27
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public Mono<CoffeeOrder> getOrder(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CoffeeOrder> create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        return orderService.create(newOrder.getCustomer(), newOrder.getItems())
            .flatMap(id -> orderService.getById(id));
    }
}
