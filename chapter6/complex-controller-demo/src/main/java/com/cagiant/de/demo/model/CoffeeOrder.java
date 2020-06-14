package com.cagiant.de.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = "handler")
public class CoffeeOrder implements Serializable {
    private Long id;
    private String customer;
    private Date createTime;
    private Date updateTime;
    private OrderState state;
    private List<Coffee> coffees;
}
