package com.cagiant.de.demo.model;

public enum OrderState {
    INIT(0),
    PAID(1),
    BREWING(2),
    BREWED(3),
    TAKEN(4),
    CANCELLED(5);

    int value;

    private OrderState(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static OrderState value2OrderState(int value) {
        for (OrderState orderState : OrderState.values()) {
            if (orderState.value == value) {
                return orderState;
            }
        }

        throw new IllegalArgumentException("无效的 value 值:" + value + "!");
    }
}
