package com.cagiant.de.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WaiterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}

}
