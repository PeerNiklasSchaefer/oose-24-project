package com.sse.ooseproject.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "building")
public class Building {
    //Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "building")
    private List<Room> rooms;
    @ManyToOne
    @JoinColumn(name="university_id")
    private University university;
    @OneToMany
    private List<Chair> chairs;

    //No-argument constructor
    public Building(){}

    //Constructor
    public Building(String name, List<Room> rooms, University university) {
        this.name = name;
        this.rooms = rooms;
        this.university = university;
    }

    //Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }
}
