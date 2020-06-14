package com.cagiant.de.demo.service;

import com.cagiant.de.demo.dao.CoffeeMapper;
import com.cagiant.de.demo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CoffeeService {

    @Resource
    private CoffeeMapper coffeeMapper;

    public List<Coffee> findAllCoffee() {
        return coffeeMapper.getAllWithParam(1, 0);
    }

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
        Optional<Coffee> coffee = coffeeMapper.getOneCoffee(name);
        return coffee;
    }

    public Coffee getCoffee(Long id) {
        return coffeeMapper.getCoffeeByCoffeeId(id);
    }

    public Optional<Coffee> getCoffee(String name) {
        return coffeeMapper.getOneCoffee(name);
    }
}
