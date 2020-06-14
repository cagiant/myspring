package com.cagiant.de.demo.controller.request;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

@Getter
@Setter
@ToString
public class NewCoffeeRequest {
    @NotNull
    private String name;
    @NotNull
    private Money price;
}
