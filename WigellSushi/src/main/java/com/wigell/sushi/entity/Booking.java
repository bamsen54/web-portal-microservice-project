package com.wigell.sushi.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;
    private int numGuests;
    private double totalPriceSek;
    private double totalPriceJpy;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToMany
    @JoinTable(
            name = "booking_dish",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

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

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public List<Dish> getDishes() { return dishes; }
    public void setDishes(List<Dish> dishes) { this.dishes = dishes; }
}