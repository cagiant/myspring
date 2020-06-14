package com.cagiant.de.demo.dao;

import com.cagiant.de.demo.model.CoffeeOrder;
import com.cagiant.de.demo.model.CoffeeOrderRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CoffeeOrderMapper {
    void insert(@Param("order") CoffeeOrder order);

    void insertRelation(@Param("coffeeOrderRelation") CoffeeOrderRelation coffeeOrderRelation);

    void updateStateById(@Param("order") CoffeeOrder order, @Param("id") Long id);

    CoffeeOrder getById(@Param("id") Long id);
}
