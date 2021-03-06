package com.cagiant.de.demo;

import com.cagiant.de.demo.model.Coffee;
import com.cagiant.de.demo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

@SpringBootApplication
@Slf4j
@EnableCaching
public class WaiterServiceApplication implements ApplicationRunner {

	@Autowired
	private CoffeeService coffeeService;

	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}

	@Bean
	public RedisTemplate<String, Coffee> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Coffee> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		Optional<Coffee> c = coffeeService.findOneCoffee("mocha");
		log.info("coffee {}", c);

		for (int i=0; i<5; i++) {
			c= coffeeService.findOneCoffee("mocha");
		}

		log.info("value from redis: {}",c);
	}

}
