package com.wigell.sushi.controller;

import com.wigell.sushi.entity.Dish;
import com.wigell.sushi.service.DishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable int id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }

    @PostMapping
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        Dish saved = dishService.createDish(dish);
        return ResponseEntity.created(URI.create("/api/v1/dishes/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable int id, @RequestBody Dish dish) {
        return ResponseEntity.ok(dishService.updateDish(id, dish));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable int id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }
}