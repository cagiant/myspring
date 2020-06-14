package com.cagiant.de.demo.service;

import com.cagiant.de.demo.dao.CoffeeMapper;
import com.cagiant.de.demo.model.Coffee;
import com.cagiant.de.demo.model.CoffeeCache;
import com.cagiant.de.demo.repository.CoffeeCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeMapper coffeeMapper;
    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;

    public List<Coffee> findAllCoffee() {
        return coffeeMapper.getAllWithParam(1, 0);
    }

    public Optional<Coffee> findSimpleCoffeeFromCache(String name) {
        Optional<CoffeeCache> cached = coffeeCacheRepository.findOneByName(name);
        if (cached.isPresent()) {
            CoffeeCache coffeeCache = cached.get();
            Coffee coffee = Coffee.builder()
                    .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .id(coffeeCache.getId())
                    .build();
            log.info("Coffee {} found in cache", coffee);
            return Optional.of(coffee);
        } else {
            Optional<Coffee> raw = findOneCoffee(name);
            raw.ifPresent(c -> {
                CoffeeCache coffeeCache = CoffeeCache.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .price(c.getPrice())
                        .build();
                log.info("Save Coffee {} to cache", coffeeCache);
                coffeeCacheRepository.save(coffeeCache);
            });
            return raw;
        }
    }

    public Optional<Coffee> findOneCoffee(String name) {
        return coffeeMapper.getOneCoffee(name);
    }
}
