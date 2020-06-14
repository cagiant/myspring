package com.example.simple.resttemplate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import org.joda.money.Money;
import java.util.Date;

/**
 * @author guokaiqiang
 * @date 2020/6/14 15:54
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {
    private Long id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
