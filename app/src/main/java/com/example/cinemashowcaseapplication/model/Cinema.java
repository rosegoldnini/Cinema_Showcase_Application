package com.example.cinemashowcaseapplication.model;

public class Cinema {
    private String name;
    private String description;

    //Look at Movie class and constructors for similar notes
    public Cinema() {
    }

    public Cinema(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
