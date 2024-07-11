package com.sse.ooseproject.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chair")
public class Chair extends OrganizationalUnit{
    //Properties
    @ManyToOne
    @JoinColumn(name="institute_id")
    private Institute institute;

    @OneToOne
    @JoinColumn(name="owner_id")
    private Employee owner;

    @ManyToOne
    @JoinColumn(name="building_id")
    private Building chairBuilding;

    @OneToMany(mappedBy = "chair")
    private List<Course> courses;

    //Constructor
    public Chair() {}

    public Chair(String name, Institute institute, Employee owner, Building chairBuilding, List<Course> courses) {
        super(name);
        this.institute = institute;
        this.owner = owner;
        this.chairBuilding = chairBuilding;
        this.courses = courses;
    }

    //Methods
    public Institute getInstitute() {
        return institute;
    }
    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Employee getOwner() {
        return owner;
    }
    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public Building getChairBuilding() {
        return chairBuilding;
    }
    public void setChairBuilding(Building chairBuilding) {
        this.chairBuilding = chairBuilding;
    }

    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
