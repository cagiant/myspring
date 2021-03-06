package com.cagiant.de.demo.service;

import com.cagiant.de.demo.dao.CoffeeMapper;
import com.cagiant.de.demo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CoffeeService {
    private static final String CACHE = "springbucks-coffee";
    @Resource
    private CoffeeMapper coffeeMapper;
    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    public List<Coffee> findAllCoffee() {
        return coffeeMapper.getAllWithParam(1, 0);
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
}
