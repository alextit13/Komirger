package com.mukmenev.android.findjob.classes;

import java.io.Serializable;

public class FindQuestionParameters implements Serializable{
    private String title;
    private boolean premium;
    private int price_from;
    private int price_to;
    private boolean only_photo;
    private String category;
    private String city;


    public FindQuestionParameters() {
    }

    public FindQuestionParameters(String title, boolean premium, int price_from, int price_to, boolean only_photo, String category, String city) {
        this.title = title;
        this.premium = premium;
        this.price_from = price_from;
        this.price_to = price_to;
        this.only_photo = only_photo;
        this.category = category;
        this.city = city;
    }

    @Override
    public String toString() {
        return "FindQuestionParameters{" +
                "title='" + title + '\'' +
                ", premium=" + premium +
                ", price_from=" + price_from +
                ", price_to=" + price_to +
                ", only_photo=" + only_photo +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getPrice_from() {
        return price_from;
    }

    public void setPrice_from(int price_from) {
        this.price_from = price_from;
    }

    public int getPrice_to() {
        return price_to;
    }

    public void setPrice_to(int price_to) {
        this.price_to = price_to;
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
