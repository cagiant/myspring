package com.cagiant.de.demo.service;

import com.cagiant.de.demo.dao.CoffeeMapper;
import com.cagiant.de.demo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {
    private static final String CACHE = "springbucks-coffee";
    @Resource
    private CoffeeMapper coffeeMapper;
    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    public List<Coffee> findAllCoffee() {
        return coffeeMapper.getAllWithParam(1, 0);
    }


    @Cacheable
    public List<Coffee> getAllCoffee() {
        return coffeeMapper.getAllCoffee();
    }

    public List<Coffee> getCoffeeByNames(List<String> names) {
        return coffeeMapper.getCoffeeByNames(names);
    }

    public Coffee saveCoffee(String name, Money price) {
        Coffee coffee = Coffee.builder()
                .price(price)
                .createTime(new Date())
                .updateTime(new Date())
                .name(name)
                .build();
        coffeeMapper.saveCoffee(coffee);

        return coffee;
    }

    public Optional<Coffee> findOneCoffee(String name) {
        HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
        if (redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, name)) {
            log.info("get coffee from redis: {}", name);
            return Optional.of(hashOperations.get(CACHE, name));
        }
        Optional<Coffee> coffee = coffeeMapper.getOneCoffee(name);
        log.info("coffee found: {}", coffee);
        if (coffee.isPresent()) {
            log.info("put coffee {} to redis", name);
            hashOperations.put(CACHE, name, coffee.get());
            redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);
        }
        return coffee;
    }

    public Optional<Coffee> getCoffee(Long id) {
        return coffeeMapper.getCoffeeByCoffeeId(id);
    }

    public Optional<Coffee> getCoffee(String name) {
        return coffeeMapper.getOneCoffee(name);
    }
}
