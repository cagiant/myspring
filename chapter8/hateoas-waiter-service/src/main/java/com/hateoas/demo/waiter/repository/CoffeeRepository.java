package com.hateoas.demo.waiter.repository;

import com.hateoas.demo.waiter.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/6/17 17:40
 */
@RepositoryRestResource(path = "coffee")
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> list);
    Coffee findByName(String name);
}
