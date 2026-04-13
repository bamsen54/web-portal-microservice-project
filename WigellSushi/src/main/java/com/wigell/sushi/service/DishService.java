package com.wigell.sushi.service;

import com.wigell.sushi.entity.Dish;
import com.wigell.sushi.repository.DishRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DishService {

    private static final Logger logger = LoggerFactory.getLogger(DishService.class);

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish getDishById(int id) {
        return dishRepository.findById(id).orElse(null);
    }

    public Dish createDish(Dish dish) {
        Dish saved = dishRepository.save(dish);
        logger.info("admin created dish {}", saved.getId());
        return saved;
    }

    public Dish updateDish(int id, Dish dish) {
        dish.setId(id);
        Dish saved = dishRepository.save(dish);
        logger.info("admin updated dish {}", id);
        return saved;
    }

    public void deleteDish(int id) {
        dishRepository.deleteById(id);
        logger.info("admin deleted dish {}", id);
    }
}