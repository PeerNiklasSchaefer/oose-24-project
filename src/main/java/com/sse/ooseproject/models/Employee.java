package com.sse.ooseproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee extends Person{
    //Properties
    private int staffNr;
    private boolean isProfessor;
    @ManyToOne
    @JoinColumn(name="university_id")
    private University university;
    @OneToOne
    @JoinColumn(name="chair_id")
    private Chair chair;

    //No-argument constructor
    public Employee(){
        super();
    }

    //Constructor
    public Employee(String firstName, String lastName, String email, int staffNr, boolean isProfessor, University university) {
        super(firstName, lastName, email);
        this.staffNr = staffNr;
        this.isProfessor = isProfessor;
        this.university = university;
    }

    //Methods
    public int getStaffNr() {
        return staffNr;
    }
    public void setStaffNr(int staffNr) {
        this.staffNr = staffNr;
    }

    public Boolean getIsProfessor() {
        return isProfessor;
    }
    public void setIsProfessor(boolean isProfessor) {
        this.isProfessor = isProfessor;
    }

    public University getUniversity() {
        return university;
    }
    public void setUniversity(University university) {
        this.university = university;
    }

    public Chair getChair() {
        return chair;
    }
    public void setChair(Chair chair) {
        this.chair = chair;
    }
}
