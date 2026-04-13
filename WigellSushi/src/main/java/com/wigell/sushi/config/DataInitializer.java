package com.wigell.sushi.config;

import com.wigell.sushi.entity.*;
import com.wigell.sushi.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DishRepository dishRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CustomerRepository customerRepository, DishRepository dishRepository,
                           RoomRepository roomRepository, BookingRepository bookingRepository,
                           OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.dishRepository = dishRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (customerRepository.findByUsername("admin") == null) {
            customerRepository.saveAll(List.of(
                    createCustomer("admin", "ADMIN", "Admin", "User"),
                    createCustomer("a", "ADMIN", "Admin", "Test"),
                    createCustomer("u", "USER", "User", "Test"),
                    createCustomer("lucas", "USER", "Lucas", "Celik"),
                    createCustomer("sara", "USER", "Sara", "Johansson"),
                    createCustomer("erik", "USER", "Erik", "Lindgren"),
                    createCustomer("anna", "USER", "Anna", "Svensson")
            ));
        }

        if (dishRepository.count() == 0) {
            dishRepository.saveAll(List.of(
                    createDish("Salmon Nigiri", "Fresh salmon slice on top of sushi rice.", 105.0),
                    createDish("Spicy Tuna Roll", "Spicy tuna, avocado, and cucumber wrapped in rice and seaweed.", 100.0),
                    createDish("Dragon Roll", "Eel and cucumber topped with avocado and spicy mayo.", 120.0),
                    createDish("Vegetarian Maki", "Avocado, cucumber, and pickled radish wrapped in seaweed and rice.", 95.0),
                    createDish("Tempura Shrimp Roll", "Crispy tempura shrimp with cucumber and a sweet soy glaze.", 115.0)
            ));
        }

        if (roomRepository.count() == 0) {
            roomRepository.saveAll(List.of(
                    createRoom("Sakura Room", 20, "Projector, Sound System"),
                    createRoom("Zen Room", 15, "LED TV, Wireless Audio"),
                    createRoom("Samurai Hall", 50, "Stage, Microphone, Lighting")
            ));
        }

        if (bookingRepository.count() == 0) {
            Customer lucas = customerRepository.findByUsername("lucas");
            Customer sara = customerRepository.findByUsername("sara");
            Room room1 = roomRepository.findById(1).orElse(null);
            Room room3 = roomRepository.findById(3).orElse(null);
            List<Dish> dishes = dishRepository.findAll();

            Booking booking1 = new Booking();
            booking1.setCustomer(lucas);
            booking1.setRoom(room1);
            booking1.setDate(LocalDate.of(2026, 4, 20));
            booking1.setNumGuests(10);
            booking1.setTotalPriceSek(1050.0);
            booking1.setTotalPriceJpy(15750.0);
            booking1.setDishes(List.of(dishes.get(0), dishes.get(1)));
            bookingRepository.save(booking1);

            Booking booking2 = new Booking();
            booking2.setCustomer(sara);
            booking2.setRoom(room3);
            booking2.setDate(LocalDate.of(2026, 4, 25));
            booking2.setNumGuests(20);
            booking2.setTotalPriceSek(2000.0);
            booking2.setTotalPriceJpy(30000.0);
            booking2.setDishes(List.of(dishes.get(2), dishes.get(3)));
            bookingRepository.save(booking2);
        }

        if (orderRepository.count() == 0) {
            Customer lucas = customerRepository.findByUsername("lucas");
            Customer sara = customerRepository.findByUsername("sara");
            List<Dish> dishes = dishRepository.findAll();

            Order order1 = new Order();
            order1.setCustomer(lucas);
            order1.setOrderDate(LocalDate.of(2026, 4, 10));
            order1.setTotalPriceSek(205.0);
            order1.setTotalPriceJpy(3075.0);
            order1.setDishes(List.of(dishes.get(0), dishes.get(1)));
            orderRepository.save(order1);

            Order order2 = new Order();
            order2.setCustomer(sara);
            order2.setOrderDate(LocalDate.of(2026, 4, 10));
            order2.setTotalPriceSek(315.0);
            order2.setTotalPriceJpy(4725.0);
            order2.setDishes(List.of(dishes.get(2), dishes.get(4)));
            orderRepository.save(order2);
        }
    }

    private Customer createCustomer(String username, String role, String firstName, String lastName) {
        Customer c = new Customer();
        c.setUsername(username);
        c.setPassword(passwordEncoder.encode("password123"));
        c.setRole(role);
        c.setFirstName(firstName);
        c.setLastName(lastName);
        return c;
    }

    private Dish createDish(String name, String description, double price) {
        Dish d = new Dish();
        d.setName(name);
        d.setDescription(description);
        d.setPriceSek(price);
        return d;
    }

    private Room createRoom(String name, int maxGuests, String equipment) {
        Room r = new Room();
        r.setName(name);
        r.setMaxGuests(maxGuests);
        r.setTechnicalEquipment(equipment);
        return r;
    }
}