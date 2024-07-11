package com.sse.ooseproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "teaching_shift")
public class TeachingShift {
    //Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String semester;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    //No-argument constructor
    public TeachingShift(){}

    //Constructor
    public TeachingShift(String semester, Course course, Employee employee) {
        this.semester = semester;
        this.course = course;
        this.employee = employee;
    }

    //Methods
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
