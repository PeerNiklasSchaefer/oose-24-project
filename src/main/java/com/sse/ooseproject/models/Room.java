package com.sse.ooseproject.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    //Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    private String number;
    private int seats;
    private boolean isAuditorium;
    @OneToMany(mappedBy = "room")
    private List<RoomOccupancy> occupancies;
    @ManyToOne
    @JoinColumn(name="building_id")
    private Building building;

    //No-argument constructor
    public Room(){}

    //Constructor
    public Room(String number, int seats, boolean isAuditorium, List<RoomOccupancy> occupancies, Building building) {
        this.number = number;
        this.seats = seats;
        this.isAuditorium = isAuditorium;
        this.occupancies = occupancies;
        this.building = building;
    }

    //Methods
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isAuditorium() {
        return isAuditorium;
    }

    public void setAuditorium(boolean auditorium) {
        isAuditorium = auditorium;
    }

    public List<RoomOccupancy> getOccupancies() {
        return occupancies;
    }

    public void setOccupancies(List<RoomOccupancy> occupancies) {
        this.occupancies = occupancies;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
