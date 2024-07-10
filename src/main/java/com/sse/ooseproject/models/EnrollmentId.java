package com.sse.ooseproject.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

@Embeddable
public class EnrollmentId {

    @JoinColumn(name = "student_id")
    private long studentId;

    @JoinColumn(name = "course_id")
    private long courseId;

    //No-argument constructor
    public EnrollmentId(){}

    //Constructor
    public EnrollmentId(long student_id, long course_id) {
        this.studentId = student_id;
        this.courseId = course_id;
    }

    public long getStudentId(){
        return this.studentId;
    }
    public void setStudentId(long studentId){
        this.studentId = studentId;
    }

    public long getCourseId(){
        return this.courseId;
    }
    public void setCourseId(long courseId){
        this.courseId = courseId;
    }
}
