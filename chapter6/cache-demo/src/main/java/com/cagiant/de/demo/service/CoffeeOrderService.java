package com.cagiant.de.demo.service;

import com.cagiant.de.demo.dao.CoffeeOrderMapper;
import com.cagiant.de.demo.model.Coffee;
import com.cagiant.de.demo.model.CoffeeOrder;
import com.cagiant.de.demo.model.CoffeeOrderRelation;
import com.cagiant.de.demo.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Transactional
@Slf4j
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderMapper coffeeOrderMapper;

    public CoffeeOrder getOrder(Long id) {
        return coffeeOrderMapper.getById(id);
    }

    public CoffeeOrder createOrder(String customer, Coffee...coffees) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .coffees(new ArrayList<>(Arrays.asList(coffees)))
                .state(OrderState.INIT)
                .build();
        coffeeOrderMapper.insert(order);
        log.info("coffee order: {}", order);

        for(Coffee coffee: coffees) {
            CoffeeOrderRelation coffeeOrderRelation = CoffeeOrderRelation.builder()
                    .itemsId(coffee.getId())
                    .coffeeOrderId(order.getId())
                    .build();
            log.info("coffee order relation: {}", coffeeOrderRelation);
            coffeeOrderMapper.insertRelation(coffeeOrderRelation);
        }

        return order;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong state order {} : {}", state, order.getState());
            return false;
        }
        order.setState(state);
        coffeeOrderMapper.updateStateById(order, order.getId());
        log.info("update order: {}", order);
        return true;
    }


}
