package com.cagiant.de.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeOrder implements Serializable {
    private Long id;
    private String customer;
    private Date createTime;
    private Date updateTime;
    private OrderState state;
    private List<Coffee> coffees;
}
