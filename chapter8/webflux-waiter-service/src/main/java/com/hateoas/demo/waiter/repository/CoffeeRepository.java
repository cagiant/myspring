package com.hateoas.demo.waiter.repository;

import com.hateoas.demo.waiter.model.Coffee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import reactor.core.publisher.Mono;

/**
 * @author guokaiqiang
 * @date 2020/6/17 17:40
 */

public interface CoffeeRepository extends R2dbcRepository<Coffee, Long> {
    @Query("select * from t_coffee where name=$1")
    Mono<Coffee> findByName(String name);
}
