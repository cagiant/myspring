package com.cagiant.de.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrderRelation implements Serializable {
    private Long coffeeOrderId;
    private Long itemsId;
}