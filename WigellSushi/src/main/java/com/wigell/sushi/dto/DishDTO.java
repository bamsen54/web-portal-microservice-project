package com.wigell.sushi.dto;

public class DishDTO {
    private int id;
    private String name;
    private String description;
    private double priceSek;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPriceSek() { return priceSek; }
    public void setPriceSek(double priceSek) { this.priceSek = priceSek; }
}