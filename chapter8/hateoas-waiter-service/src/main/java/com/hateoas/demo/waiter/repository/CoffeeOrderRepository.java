package com.hateoas.demo.waiter.repository;

import com.hateoas.demo.waiter.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author guokaiqiang
 * @date 2020/6/17 17:39
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
