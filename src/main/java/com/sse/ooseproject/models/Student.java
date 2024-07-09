package com.sse.ooseproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student extends Person{
    //Properties
    private int matNr;

    private String studySubject;

    @ManyToOne
    @JoinColumn(name="university_id")
    private University university;

    //No-argument constructor
    public Student(){
        super();
    }

    //Constructor
    public Student(String firstName, String lastName, String email, int matNr, String studySubject, University university) {
        super(firstName, lastName, email);
        this.matNr = matNr;
        this.studySubject = studySubject;
        this.university = university;
    }

    //Methods
    public int getMatNr(){
        return matNr;
    }
    public void setMatNr(int matNr) {
        this.matNr = matNr;
    }

    public String getStudySubject(){
        return studySubject;
    }
    public void setStudySubject(String studySubject) {
        this.studySubject = studySubject;
    }

    public University getUniversity() {
        return university;
    }
    public void setUniversity(University university) {
        this.university = university;
    }
}
