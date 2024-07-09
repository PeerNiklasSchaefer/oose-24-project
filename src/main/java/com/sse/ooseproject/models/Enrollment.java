package com.sse.ooseproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollment")
public class Enrollment {
    //Properties
    @EmbeddedId
    private EnrollmentId id = new EnrollmentId();

    private String semester;

    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    private Course course;

    //No-argument constructor
    public Enrollment(){}

    //Constructor
    public Enrollment(String semester, Student student, Course course) {
        this.semester = semester;
        this.student = student;
        this.course = course;
        this.id = new EnrollmentId(student.getId(), course.getId());
    }

    //Methods
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Student getStudent(){return this.student;}

    public void setStudent(Student student){
        this.student = student;
        this.id.setStudent(student.getId());
    }

    public Course getCourse(){return this.course;}

    public void setCourse(Course course){
        this.course = course;
        this.id.setCourse(course.getId());
    }
}
