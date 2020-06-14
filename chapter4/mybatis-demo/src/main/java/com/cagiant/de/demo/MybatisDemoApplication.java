package com.cagiant.de.demo;

import com.cagiant.de.demo.mapper.CoffeeMapper;
import com.cagiant.de.demo.model.Coffee;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("com.cagiant.de.demo.mapper")
public class MybatisDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeMapper coffeeMapper;

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		coffeeMapper.findAllWithRowBounds(new RowBounds(1,3))
				.forEach(c -> log.info("Page(1) coffee {}",c));
		coffeeMapper.findAllWithRowBounds(new RowBounds(2,3))
				.forEach(c -> log.info("Page(2) coffee {}", c));
		log.info("================");

		coffeeMapper.findAllWithRowBounds(new RowBounds(1, 0))
				.forEach(c -> log.info("Page(1) coffee {}", c));

		log.info("================");

		coffeeMapper.findAllWithParam(1,3)
				.forEach(c -> log.info("Page (1) coffee {}", c));
		List<Coffee> list = coffeeMapper.findAllWithParam(2,3);
		PageInfo page = new PageInfo(list);
		log.info("page info: {}", page);
	}

}
