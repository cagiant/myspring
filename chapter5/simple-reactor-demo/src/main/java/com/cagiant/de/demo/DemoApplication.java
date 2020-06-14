package com.cagiant.de.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class DemoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		Flux.range(1, 6)
				.doOnRequest(n -> log.info("Requst {} number", n))
				.publishOn(Schedulers.elastic())
				.doOnComplete(() -> log.info("publish complete 1"))
				.map(i -> {
					log.info("Publish {}, {}", Thread.currentThread(),i);
					return 10 /(i - 3);
//					return i;
				})
				.publishOn(Schedulers.single())
				.doOnComplete(() -> log.info("publish complete 2"))
//				.subscribeOn(Schedulers.single())
				.onErrorResume(e -> {
					log.error("Exception{}", e.toString());
					return Mono.just(-1);
				})
				.onErrorReturn(-1)
				.subscribe(i -> log.info("Subscribe {} :{}", Thread.currentThread(), i),
						e -> log.info("error {}", e.toString()),
						() ->log.info("Subscribe complete")
				);
		Thread.sleep(2000);
	}
}
