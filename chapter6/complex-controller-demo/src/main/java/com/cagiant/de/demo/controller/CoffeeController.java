package com.cagiant.de.demo.controller;

import com.cagiant.de.demo.model.Coffee;
import com.cagiant.de.demo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(path = "/", params = "!name")
    @ResponseBody
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<Coffee> getById(@PathVariable Long id) {
        Optional<Coffee> coffee = coffeeService.getCoffee(id);
        return coffee;
    }

    @GetMapping(path = "/", params = "name")
    @ResponseBody
    public Optional<Coffee> getByName(String name) {
        return coffeeService.getCoffee(name);
    }
}
