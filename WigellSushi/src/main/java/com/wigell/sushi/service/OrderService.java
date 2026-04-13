package com.wigell.sushi.service;

import com.wigell.sushi.dto.CustomerDTO;
import com.wigell.sushi.dto.DishDTO;
import com.wigell.sushi.dto.OrderDTO;
import com.wigell.sushi.entity.Order;
import com.wigell.sushi.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private OrderDTO toDTO(Order o) {
        OrderDTO dto = new OrderDTO();
        dto.setId(o.getId());
        dto.setOrderDate(o.getOrderDate());
        dto.setTotalPriceSek(o.getTotalPriceSek());
        dto.setTotalPriceJpy(o.getTotalPriceJpy());

        if (o.getCustomer() != null) {
            CustomerDTO c = new CustomerDTO();
            c.setId(o.getCustomer().getId());
            c.setUsername(o.getCustomer().getUsername());
            c.setFirstName(o.getCustomer().getFirstName());
            c.setLastName(o.getCustomer().getLastName());
            dto.setCustomer(c);
        }

        if (o.getDishes() != null) {
            dto.setDishes(o.getDishes().stream().map(d -> {
                DishDTO dd = new DishDTO();
                dd.setId(d.getId());
                dd.setName(d.getName());
                dd.setDescription(d.getDescription());
                dd.setPriceSek(d.getPriceSek());
                return dd;
            }).toList());
        }

        return dto;
    }

    public OrderDTO getOrderById(int id) {
        return orderRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public List<OrderDTO> getOrdersByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId).stream().map(this::toDTO).toList();
    }

    public OrderDTO createOrder(Order order) {
        Order saved = orderRepository.save(order);
        logger.info("user created order {}", saved.getId());
        return toDTO(saved);
    }
}