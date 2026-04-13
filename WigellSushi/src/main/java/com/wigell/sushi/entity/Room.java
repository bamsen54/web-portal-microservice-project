package com.wigell.sushi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int maxGuests;
    private String technicalEquipment;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMaxGuests() { return maxGuests; }
    public void setMaxGuests(int maxGuests) { this.maxGuests = maxGuests; }

    public String getTechnicalEquipment() { return technicalEquipment; }
    public void setTechnicalEquipment(String technicalEquipment) { this.technicalEquipment = technicalEquipment; }
}