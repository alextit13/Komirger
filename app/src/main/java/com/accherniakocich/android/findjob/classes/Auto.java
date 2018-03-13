package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class Auto extends Ad implements Serializable{

    private String constitance;
    private String marka;
    private String model;
    private String type_body;
    private String color;
    private String region;
    private String kategory;
    private int year;
    private long road;
    private String type_engine;
    private double values_engine;
    private String transmission;
    private boolean isConvert;
    private boolean rents;

    public Auto() {
    }

    public Auto(String nameAd, String nameJobAd, String textAd, String imagePathAd_1, String imagePathAd_2, String imagePathAd_3, String imagePathAd_4, String imagePathAd_5, int costAd, String peopleSourceAd, long dateAd, String type_money, User user, int numOfShowAd, String inFavoritUsers, boolean isCheck, String city, String categoty, boolean premium, int activate) {
        super(nameAd, nameJobAd, textAd, imagePathAd_1, imagePathAd_2, imagePathAd_3, imagePathAd_4, imagePathAd_5, costAd, peopleSourceAd, dateAd, type_money, user, numOfShowAd, inFavoritUsers, isCheck, city, categoty, premium, activate);
    }

    public String getConstitance() {
        return constitance;
    }

    public void setConstitance(String constitance) {
        this.constitance = constitance;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType_body() {
        return type_body;
    }

    public void setType_body(String type_body) {
        this.type_body = type_body;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getKategory() {
        return kategory;
    }

    public void setKategory(String kategory) {
        this.kategory = kategory;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getRoad() {
        return road;
    }

    public void setRoad(long road) {
        this.road = road;
    }

    public String getType_engine() {
        return type_engine;
    }

    public void setType_engine(String type_engine) {
        this.type_engine = type_engine;
    }

    public double getValues_engine() {
        return values_engine;
    }

    public void setValues_engine(double values_engine) {
        this.values_engine = values_engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public boolean isConvert() {
        return isConvert;
    }

    public void setConvert(boolean convert) {
        isConvert = convert;
    }

    public boolean isRents() {
        return rents;
    }

    public void setRents(boolean rents) {
        this.rents = rents;
    }
}
