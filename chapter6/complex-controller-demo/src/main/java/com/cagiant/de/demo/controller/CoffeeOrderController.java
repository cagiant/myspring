package com.cagiant.de.demo.controller;

import com.cagiant.de.demo.controller.request.NewOrderRequest;
import com.cagiant.de.demo.model.Coffee;
import com.cagiant.de.demo.model.CoffeeOrder;
import com.cagiant.de.demo.service.CoffeeOrderService;
import com.cagiant.de.demo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
        return coffeeOrderService.getOrder(id);
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrderRequest) {
        log.info("received new order: {}", newOrderRequest.getItems());
        Coffee[] coffeeList = coffeeService.getCoffeeByNames(newOrderRequest.getItems()).toArray(new Coffee[] {});

        return coffeeOrderService.createOrder(newOrderRequest.getCustomer(), coffeeList);
    }

}
