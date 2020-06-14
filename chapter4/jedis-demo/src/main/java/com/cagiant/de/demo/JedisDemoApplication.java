package com.cagiant.de.demo;

import com.cagiant.de.demo.mapper.CoffeeMapper;
import com.cagiant.de.demo.model.Coffee;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
@MapperScan("com.cagiant.de.demo.mapper")
public class JedisDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeMapper coffeeMapper;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private JedisPoolConfig jedisPoolConfig;

	public static void main(String[] args) {
		SpringApplication.run(JedisDemoApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("redis")
	public JedisPoolConfig jedisPoolConfig() {
		return new JedisPoolConfig();
	}

	@Bean(destroyMethod = "close")
	public JedisPool jedisPool(@Value("${redis.host}") String host) {
		return new JedisPool(jedisPoolConfig(), host);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info(jedisPoolConfig.toString());

		try(Jedis jedis = jedisPool.getResource()) {
			coffeeMapper.findAllWithParam(1, 0)
					.forEach(c -> {
						jedis.hset(
								"springbucks-menu",
								c.getName(),
								Long.toString(c.getPrice().getAmountMinorLong()));
					});

			Map<String, String> menu =  jedis.hgetAll("springbucks-menu");
			log.info("Menu: {}", menu);

			String price = jedis.hget("springbucks-menu", "espresso");
			log.info("espresso- {}",
					Money.ofMinor(CurrencyUnit.of("CNY"),Long.parseLong(price)));
		}
	}

}
