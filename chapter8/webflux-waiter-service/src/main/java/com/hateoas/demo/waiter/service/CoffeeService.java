package com.hateoas.demo.waiter.service;

import com.hateoas.demo.waiter.model.Coffee;
import com.hateoas.demo.waiter.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author guokaiqiang
 * @date 2020/6/20 21:38
 */
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public Mono<Coffee> findById(Long id) {
        return coffeeRepository.findById(id);
    }

    public Flux<Coffee> findAll() {
        return coffeeRepository.findAll();
    }

    public Mono<Coffee> findByName(String name) {
        return coffeeRepository.findByName(name);
    }
}
