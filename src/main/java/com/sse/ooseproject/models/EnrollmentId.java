package com.sse.ooseproject.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Embeddable
public class EnrollmentId {
   private long studentId;

   private long courseId;

    //No-argument constructor
    public EnrollmentId(){}

    //Constructor
    public EnrollmentId(long studentId, long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public long getStudentId(){
        return this.studentId;
    }
    public void setStudent(long studentId){
        this.studentId = studentId;
    }

    public long getCourseId(){
        return this.courseId;
    }
    public void setCourse(long courseId){
        this.courseId = courseId;
    }
}
