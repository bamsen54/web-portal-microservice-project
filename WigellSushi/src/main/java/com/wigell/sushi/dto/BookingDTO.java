package com.wigell.sushi.dto;

import java.time.LocalDate;
import java.util.List;

public class BookingDTO {
    private int id;
    private LocalDate date;
    private int numGuests;
    private double totalPriceSek;
    private double totalPriceJpy;
    private CustomerDTO customer;
    private RoomDTO room;
    private List<DishDTO> dishes;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getNumGuests() { return numGuests; }
    public void setNumGuests(int numGuests) { this.numGuests = numGuests; }

    public double getTotalPriceSek() { return totalPriceSek; }
    public void setTotalPriceSek(double totalPriceSek) { this.totalPriceSek = totalPriceSek; }

    public double getTotalPriceJpy() { return totalPriceJpy; }
    public void setTotalPriceJpy(double totalPriceJpy) { this.totalPriceJpy = totalPriceJpy; }

    public CustomerDTO getCustomer() { return customer; }
    public void setCustomer(CustomerDTO customer) { this.customer = customer; }

    public RoomDTO getRoom() { return room; }
    public void setRoom(RoomDTO room) { this.room = room; }

    public List<DishDTO> getDishes() { return dishes; }
    public void setDishes(List<DishDTO> dishes) { this.dishes = dishes; }
}