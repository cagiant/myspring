package com.hateoas.demo.waiter.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author guokaiqiang
 * @date 2020/6/17 17:32
 */
@Table("t_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder  implements Serializable {
    @Id
    private Long id;
    private String customer;
    private OrderState state;
    private List<Coffee> items;
    private Date createTime;
    private Date updateTime;
}
