package com.sse.ooseproject.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "university")
public class University {
    //Properties
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "university")
    private List<Building> buildings;

    @OneToMany(mappedBy = "university")
    private List<Student> students;

    @OneToMany(mappedBy = "university")
    private List<Employee> employees;

    //No-argument constructor
    public University(){}

    //Constructor
    public University(String name, List<Building> buildings, List<Student> students, List<Employee> employees) {
        this.name = name;
        this.buildings = buildings;
        this.students = students;
        this.employees = employees;
    }

    //Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
