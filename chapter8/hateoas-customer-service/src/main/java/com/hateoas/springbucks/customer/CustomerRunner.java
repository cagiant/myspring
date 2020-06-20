package com.hateoas.springbucks.customer;

import com.hateoas.springbucks.customer.model.Coffee;
import com.hateoas.springbucks.customer.model.CoffeeOrder;
import com.hateoas.springbucks.customer.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

/**
 * @author guokaiqiang
 * @date 2020/6/17 22:07
 */
@Component
@Slf4j
public class CustomerRunner implements ApplicationRunner {
    private static final URI ROOT_URI = URI.create("http://localhost:8080/");
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Link coffeeLink = getLink(ROOT_URI, "coffees");
        readCoffeeMenu(coffeeLink);
        EntityModel<Coffee> americano = addCoffee(coffeeLink);

        Link orderLink = getLink(ROOT_URI, "coffeeOrders");
        addOrder(orderLink, americano);
        queryOrders(orderLink);
    }

    private void addOrder(Link orderLink, EntityModel<Coffee> coffee) {
        CoffeeOrder newOrder = CoffeeOrder.builder()
                .customer("Han Meimei")
                .state(OrderState.INIT)
                .build();
        RequestEntity<?> req =
                RequestEntity.post(orderLink.getTemplate().expand()).body(newOrder);
        ResponseEntity<EntityModel<CoffeeOrder>> resp =
                restTemplate.exchange(req,
                        new ParameterizedTypeReference<EntityModel<CoffeeOrder>>() {});
        log.info("add order response: {}", resp);

        EntityModel<CoffeeOrder> order = resp.getBody();
        Optional<Link> items = order.getLink("items");
        req = RequestEntity.post(items.get().getTemplate().expand()).body(Collections.singletonMap("_links", coffee.getLink("self")));
        ResponseEntity<String> itemResp = restTemplate.exchange(req, String.class);
        log.info("add Order items response: {}", itemResp);
    }

    private void queryOrders(Link link) {
        ResponseEntity<String> resp = restTemplate.getForEntity(link.getTemplate().expand(), String.class);
        log.info("query order response: {}", resp);
    }

    private EntityModel<Coffee> addCoffee(Link coffeeLink) {
        Coffee americano = Coffee.builder()
                .name("americano")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.0))
                .build();
        RequestEntity<Coffee> req =
                RequestEntity.post(coffeeLink.getTemplate().expand()).body(americano);
        ResponseEntity<EntityModel<Coffee>> resp =
                restTemplate.exchange(req,
                        new ParameterizedTypeReference<EntityModel<Coffee>>(){ });
        log.info("add coffee response: {}", resp);
        return resp.getBody();
    }

    private void readCoffeeMenu(Link coffeeLink) {
        ResponseEntity<PagedModel<CollectionModel<Link>>> coffeeResp
                = restTemplate.exchange(coffeeLink.getTemplate().expand(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<CollectionModel<Link>>>(){});
        log.info("Menu Response: {}", coffeeResp.getBody());
    }

    private Link getLink(URI uri, String rel) {
        ResponseEntity<CollectionModel<Link>> rootResp =
                restTemplate.exchange(uri, HttpMethod.GET, null,
                        new ParameterizedTypeReference<CollectionModel<Link>>(){});
        Optional<Link> link = rootResp.getBody().getLink(rel);
        log.info("Link: {}", link);
        return link.get();
    }
}
