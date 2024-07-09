package com.sse.ooseproject.models;

import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public class OrganizationalUnit {
    //Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    //Construtor
    protected OrganizationalUnit() {}

    protected OrganizationalUnit(String name) {
        this.name = name;
    }

    //Methods
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}
