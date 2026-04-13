package com.wigell.sushi.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private int id;
    private LocalDate orderDate;
    private double totalPriceSek;
    private double totalPriceJpy;
    private CustomerDTO customer;
    private List<DishDTO> dishes;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public double getTotalPriceSek() { return totalPriceSek; }
    public void setTotalPriceSek(double totalPriceSek) { this.totalPriceSek = totalPriceSek; }

    public double getTotalPriceJpy() { return totalPriceJpy; }
    public void setTotalPriceJpy(double totalPriceJpy) { this.totalPriceJpy = totalPriceJpy; }

    public CustomerDTO getCustomer() { return customer; }
    public void setCustomer(CustomerDTO customer) { this.customer = customer; }

    public List<DishDTO> getDishes() { return dishes; }
    public void setDishes(List<DishDTO> dishes) { this.dishes = dishes; }
}