package com.cagiant.de.demo.service;

import com.cagiant.de.demo.mapper.CoffeeMapper;
import com.cagiant.de.demo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {
    @Autowired
    private CoffeeMapper coffeeMapper;

    @Cacheable
    public List<Coffee> findAllCoffee() {
        return coffeeMapper.findAllWithParam(1, 0);
    }

    @CacheEvict
    public void reloadCoffee() {
    }

    public Optional<Coffee> findOneCoffee(String name) {
        Optional<Coffee> coffee = coffeeMapper.findOneCoffee(name);
        log.info("find one coffee: {}", coffee);
        return coffee;
    }
}
