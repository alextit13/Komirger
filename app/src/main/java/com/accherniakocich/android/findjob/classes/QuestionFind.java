package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class QuestionFind implements Serializable{
    private String title;
    private int premium;
    private int cost_from;
    private int cost_to;
    private boolean only_photo;
    private String category;
    private String city;

    public QuestionFind() {
    }

    public QuestionFind(String title, int premium, int cost_from, int cost_to, boolean only_photo, String category, String city) {
        this.title = title;
        this.premium = premium;
        this.cost_from = cost_from;
        this.cost_to = cost_to;
        this.only_photo = only_photo;
        this.category = category;
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public int getCost_from() {
        return cost_from;
    }

    public void setCost_from(int cost_from) {
        this.cost_from = cost_from;
    }

    public int getCost_to() {
        return cost_to;
    }

    public void setCost_to(int cost_to) {
        this.cost_to = cost_to;
    }

    public boolean isOnly_photo() {
        return only_photo;
    }

    public void setOnly_photo(boolean only_photo) {
        this.only_photo = only_photo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
