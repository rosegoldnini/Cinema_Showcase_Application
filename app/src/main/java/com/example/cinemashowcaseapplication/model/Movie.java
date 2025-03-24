package com.example.cinemashowcaseapplication.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String genre;
    private String name;
    private String description;
    private int year;
    private int categoryId;
    private String imageFileName;

    // Default Movie constructor for JSON pursoses
    public Movie() {
    }

    //...I hope this constructor is self explanatory
    public Movie(String name, String description, int year, String genre, String imageFileName) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.genre = genre;
        this.imageFileName = imageFileName;
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

    public double getYear() {
        return year;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFormattedYear() {
        if (year < 0) {
            return "N/A";
        }
        try {
            return Integer.toString(year);
        } catch (NumberFormatException e) {
            return "N/A";
        }
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
