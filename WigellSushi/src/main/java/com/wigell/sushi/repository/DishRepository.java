package com.wigell.sushi.repository;

import com.wigell.sushi.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}