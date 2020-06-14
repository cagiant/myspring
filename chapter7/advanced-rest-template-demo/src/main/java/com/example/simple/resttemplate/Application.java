package com.example.simple.resttemplate;

import com.example.simple.resttemplate.model.Coffee;
import com.example.simple.resttemplate.support.CustomConnectionKeepAliveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class Application implements ApplicationRunner {
	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.sources(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory requestFactory() {
		PoolingHttpClientConnectionManager connectionManager =
				new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.evictIdleConnections(30,TimeUnit.SECONDS)
				.disableAutomaticRetries()
				.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
				.build();

		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory(httpClient);

		return requestFactory;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.
				setConnectTimeout(Duration.ofMillis(100))
				.setReadTimeout(Duration.ofMillis(500))
				.requestFactory(this::requestFactory)
				.build();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:8089/coffee/?name={name}")
				.build("mocha");
		RequestEntity<Void> req = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_XML)
				.build();
		ResponseEntity<String> resp  = restTemplate.exchange(req, String.class);
		log.info("Response Status: {}, Response Headers: {}", resp.getStatusCode(), resp.getHeaders().toString());
		log.info("Coffee: {}", resp.getBody());

		String coffeeUri = "http://localhost:8089/coffee/";
		Coffee request = Coffee.builder()
				.name("Americano")
				.price(Money.of(CurrencyUnit.of("CNY"), 25.00))
				.build();
		Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
		log.info("New Coffee: {}", response);

		ParameterizedTypeReference<List<Coffee>> ptr =
				new ParameterizedTypeReference<List<Coffee>>() {};
		ResponseEntity<List<Coffee>> list = restTemplate
				.exchange(coffeeUri, HttpMethod.GET, null, ptr);
		list.getBody().forEach(c -> log.info("Coffee: {}", c));
	}

}
