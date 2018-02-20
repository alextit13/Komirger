package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class Company implements Serializable{
    String name;
    String about;
    String dateID;
    String year_start;
    String type_of_work;
    int rating;
    boolean location;
    String category;

    public Company() {
    }

    public Company(String name, String about, String dateID, String year_start, String type_of_work, int rating, boolean location, String category) {
        this.name = name;
        this.about = about;
        this.dateID = dateID;
        this.year_start = year_start;
        this.type_of_work = type_of_work;
        this.rating = rating;
        this.location = location;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", dateID='" + dateID + '\'' +
                ", year_start='" + year_start + '\'' +
                ", type_of_work='" + type_of_work + '\'' +
                ", rating=" + rating +
                ", location=" + location +
                ", category='" + category + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDateID() {
        return dateID;
    }

    public void setDateID(String dateID) {
        this.dateID = dateID;
    }

    public String getYear_start() {
        return year_start;
    }

    public void setYear_start(String year_start) {
        this.year_start = year_start;
    }

    public String getType_of_work() {
        return type_of_work;
    }

    public void setType_of_work(String type_of_work) {
        this.type_of_work = type_of_work;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
