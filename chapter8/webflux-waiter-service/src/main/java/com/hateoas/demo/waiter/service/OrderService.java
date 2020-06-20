package com.hateoas.demo.waiter.service;

import com.hateoas.demo.waiter.model.CoffeeOrder;
import com.hateoas.demo.waiter.model.OrderState;
import com.hateoas.demo.waiter.repository.CoffeeOrderRepository;
import com.hateoas.demo.waiter.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/6/20 21:38
 */
@Service
public class OrderService {
    @Autowired
    private CoffeeOrderRepository orderRepository;
    @Autowired
    private CoffeeRepository coffeeRepository;

    public Mono<CoffeeOrder> getById(Long id) {
        return orderRepository.get(id);
    }

    public Mono<Long> create(String customer, List<String> items) {
        return Flux.fromStream(items.stream())
            .flatMap(n -> coffeeRepository.findByName(n))
            .collectList()
            .flatMap(l ->
                orderRepository.save(CoffeeOrder.builder()
                    .customer(customer)
                    .items(l)
                    .state(OrderState.INIT)
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build())
            );
    }
}
