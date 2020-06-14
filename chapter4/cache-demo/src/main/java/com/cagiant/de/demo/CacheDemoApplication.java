package com.cagiant.de.demo;

import com.cagiant.de.demo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("com.cagiant.de.demo.mapper")
@EnableCaching(proxyTargetClass = true)
public class CacheDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeService coffeeService;


	public static void main(String[] args) {
		SpringApplication.run(CacheDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Count {}", coffeeService.findAllCoffee().size());
		for( int i=0; i< 10; i++) {
			log.info("Reading from cache.");
			coffeeService.findAllCoffee();
		}
		coffeeService.reloadCoffee();
		log.info("Reading after refresh.");
		coffeeService.findAllCoffee().forEach(c -> log.info("Coffee found {}", c.getName()));
	}

}
