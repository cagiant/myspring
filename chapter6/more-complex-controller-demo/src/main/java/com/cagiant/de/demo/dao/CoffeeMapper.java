package com.cagiant.de.demo.dao;

import com.cagiant.de.demo.model.Coffee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CoffeeMapper {

    List<Coffee> getAllWithRowBounds(RowBounds rowBounds);


    List<Coffee> getAllWithParam(@Param("pageNum") int pageNum,
                                  @Param("pageSize") int pageSize);

    @Select("select * from my_test.t_coffee where name= #{coffeeName}")
    Optional<Coffee> getOneCoffee(@Param("coffeeName") String coffeeName);

    Integer getTest();

    List<Coffee> getCoffeeByOrderId(@Param("orderId") int orderId);

    List<Coffee> getAllCoffee();

    List<Coffee> getCoffeeByNames(@Param("names") List<String> names);

    Optional<Coffee> getCoffeeByCoffeeId(@Param("id") Long id);

    void saveCoffee(@Param("coffee") Coffee coffee);
}
