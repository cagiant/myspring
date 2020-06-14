package com.cagiant.de.demo.controller;

import com.cagiant.de.demo.controller.request.NewCoffeeRequest;
import com.cagiant.de.demo.model.Coffee;
import com.cagiant.de.demo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

//    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
//    public Coffee addCoffee(@Valid NewCoffeeRequest newCoffeeRequest,
//                            BindingResult result) {
//        if (result.hasErrors()) {
//            log.warn("Binding Errors {}", result);
//            return null;
//        }
//        return coffeeService.saveCoffee(newCoffeeRequest.getName(), newCoffeeRequest.getPrice());
//    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest newCoffeeRequest) {
        return coffeeService.saveCoffee(newCoffeeRequest.getName(), newCoffeeRequest.getPrice());
    }

    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader buffer = null;
            try {
                buffer = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String str;
                while ((str = buffer.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    if (ObjectUtils.isNotEmpty(arr) && arr.length == 2) {
                        coffees.add(coffeeService.saveCoffee(arr[0],
                                Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            } catch (IOException e) {
                log.error("exception {}", e);
            } finally {
                IOUtils.closeQuietly(buffer);
            }
        }

        return coffees;
    }


    @GetMapping(path = "/", params = "!name")
    @ResponseBody
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Coffee> getById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffee(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(coffee);
    }

    @GetMapping(path = "/", params = "name")
    @ResponseBody
    public Optional<Coffee> getByName(String name) {
        return coffeeService.getCoffee(name);
    }
}
