package com.wigell.sushi.repository;

import com.wigell.sushi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerId(int customerId);
}