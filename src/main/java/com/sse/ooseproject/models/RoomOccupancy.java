package com.sse.ooseproject.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_occupancy")
public class RoomOccupancy {
    //Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime occupancyTime;

    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    //No-argument constructor
    public RoomOccupancy(){}

    //Constructor
    public RoomOccupancy(LocalDateTime occupancyTime, Room room, Course course) {
        this.occupancyTime = occupancyTime;
        this.room = room;
        this.course = course;
    }

    //Methods
    public LocalDateTime getOccupancyTime() {
        return occupancyTime;
    }

    public void setOccupancyTime(LocalDateTime occupancyTime) {
        this.occupancyTime = occupancyTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
