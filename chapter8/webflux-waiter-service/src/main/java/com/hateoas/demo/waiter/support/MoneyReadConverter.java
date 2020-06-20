package com.hateoas.demo.waiter.support;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author guokaiqiang
 * @date 2020/6/20 21:41
 */
@ReadingConverter
public class MoneyReadConverter implements Converter<Long, Money> {
    @Override
    public Money convert(Long a) {
        return Money.ofMinor(CurrencyUnit.of("CNY"),a);
    }
}
