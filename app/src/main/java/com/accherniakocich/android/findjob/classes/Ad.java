package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class Ad implements Serializable{
    private String nameAd;
    private String nameJobAd;
    private String textAd;
    private String imagePathAd_1;
    private String imagePathAd_2;
    private String imagePathAd_3;
    private String imagePathAd_4;
    private String imagePathAd_5;
    private int costAd;
    private String peopleSourceAd;
    private long dateAd;
    private String type_money;
    private User user;
    private int numOfShowAd;
    private String inFavoritUsers;
    private boolean isCheck;
    private String city;
    private String categoty;
    private boolean premium;
    private int activate;

    public Ad() {

    }

    public Ad(boolean premium, String categoty, String nameAd, String nameJobAd, String textAd, String imagePathAd_1, String imagePathAd_2, String imagePathAd_3, String imagePathAd_4, String imagePathAd_5, int costAd, String peopleSourceAd, long dateAd, String type_money, User user, int numOfShowAd, String inFavoritUsers, boolean isCheck, String city) {
        this.nameAd = nameAd;
        this.nameJobAd = nameJobAd;
        this.textAd = textAd;
        this.imagePathAd_1 = imagePathAd_1;
        this.imagePathAd_2 = imagePathAd_2;
        this.imagePathAd_3 = imagePathAd_3;
        this.imagePathAd_4 = imagePathAd_4;
        this.imagePathAd_5 = imagePathAd_5;
        this.costAd = costAd;
        this.peopleSourceAd = peopleSourceAd;
        this.dateAd = dateAd;
        this.type_money = type_money;
        this.user = user;
        this.numOfShowAd = numOfShowAd;
        this.inFavoritUsers = inFavoritUsers;
        this.isCheck = isCheck;
        this.city = city;
        this.categoty = categoty;
        this.premium = premium;
    }

    public Ad(String nameAd, String nameJobAd, String textAd, String imagePathAd_1, String imagePathAd_2, String imagePathAd_3, String imagePathAd_4, String imagePathAd_5, int costAd, String peopleSourceAd, long dateAd, String type_money, User user, int numOfShowAd, String inFavoritUsers, boolean isCheck, String city, String categoty, boolean premium, int activate) {
        this.nameAd = nameAd;
        this.nameJobAd = nameJobAd;
        this.textAd = textAd;
        this.imagePathAd_1 = imagePathAd_1;
        this.imagePathAd_2 = imagePathAd_2;
        this.imagePathAd_3 = imagePathAd_3;
        this.imagePathAd_4 = imagePathAd_4;
        this.imagePathAd_5 = imagePathAd_5;
        this.costAd = costAd;
        this.peopleSourceAd = peopleSourceAd;
        this.dateAd = dateAd;
        this.type_money = type_money;
        this.user = user;
        this.numOfShowAd = numOfShowAd;
        this.inFavoritUsers = inFavoritUsers;
        this.isCheck = isCheck;
        this.city = city;
        this.categoty = categoty;
        this.premium = premium;
        this.activate = activate;
    }

    public Ad(boolean premium, String categoty, String nameAd, String nameJobAd, String textAd, int costAd, String peopleSourceAd, long dateAd, String type_money, User user, int numOfShowAd, String inFavoritUsers, boolean isCheck, String city) {
        this.nameAd = nameAd;
        this.nameJobAd = nameJobAd;
        this.textAd = textAd;
        this.costAd = costAd;
        this.peopleSourceAd = peopleSourceAd;
        this.dateAd = dateAd;
        this.type_money = type_money;
        this.user = user;
        this.numOfShowAd = numOfShowAd;
        this.inFavoritUsers = inFavoritUsers;
        this.isCheck = isCheck;
        this.city = city;
        this.categoty = categoty;
        this.premium = premium;
    }

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getNameAd() {
        return nameAd;
    }

    public void setNameAd(String nameAd) {
        this.nameAd = nameAd;
    }

    public String getNameJobAd() {
        return nameJobAd;
    }

    public void setNameJobAd(String nameJobAd) {
        this.nameJobAd = nameJobAd;
    }

    public String getTextAd() {
        return textAd;
    }

    public void setTextAd(String textAd) {
        this.textAd = textAd;
    }

    public String getImagePathAd_1() {
        return imagePathAd_1;
    }

    public void setImagePathAd_1(String imagePathAd_1) {
        this.imagePathAd_1 = imagePathAd_1;
    }

    public String getImagePathAd_2() {
        return imagePathAd_2;
    }

    public void setImagePathAd_2(String imagePathAd_2) {
        this.imagePathAd_2 = imagePathAd_2;
    }

    public String getImagePathAd_3() {
        return imagePathAd_3;
    }

    public void setImagePathAd_3(String imagePathAd_3) {
        this.imagePathAd_3 = imagePathAd_3;
    }

    public String getImagePathAd_4() {
        return imagePathAd_4;
    }

    public void setImagePathAd_4(String imagePathAd_4) {
        this.imagePathAd_4 = imagePathAd_4;
    }

    public String getImagePathAd_5() {
        return imagePathAd_5;
    }

    public void setImagePathAd_5(String imagePathAd_5) {
        this.imagePathAd_5 = imagePathAd_5;
    }

    public int getCostAd() {
        return costAd;
    }

    public void setCostAd(int costAd) {
        this.costAd = costAd;
    }

    public String getPeopleSourceAd() {
        return peopleSourceAd;
    }

    public void setPeopleSourceAd(String peopleSourceAd) {
        this.peopleSourceAd = peopleSourceAd;
    }

    public long getDateAd() {
        return dateAd;
    }

    public void setDateAd(long dateAd) {
        this.dateAd = dateAd;
    }

    public String getType_money() {
        return type_money;
    }

    public void setType_money(String type_money) {
        this.type_money = type_money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumOfShowAd() {
        return numOfShowAd;
    }

    public void setNumOfShowAd(int numOfShowAd) {
        this.numOfShowAd = numOfShowAd;
    }

    public String getInFavoritUsers() {
        return inFavoritUsers;
    }

    public void setInFavoritUsers(String inFavoritUsers) {
        this.inFavoritUsers = inFavoritUsers;
    }

    public String getCategoty() {
        return categoty;
    }

    public void setCategoty(String categoty) {
        this.categoty = categoty;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "nameAd='" + nameAd + '\'' +
                ", nameJobAd='" + nameJobAd + '\'' +
                ", textAd='" + textAd + '\'' +
                ", imagePathAd_1='" + imagePathAd_1 + '\'' +
                ", imagePathAd_2='" + imagePathAd_2 + '\'' +
                ", imagePathAd_3='" + imagePathAd_3 + '\'' +
                ", imagePathAd_4='" + imagePathAd_4 + '\'' +
                ", imagePathAd_5='" + imagePathAd_5 + '\'' +
                ", costAd=" + costAd +
                ", peopleSourceAd='" + peopleSourceAd + '\'' +
                ", dateAd=" + dateAd +
                ", type_money='" + type_money + '\'' +
                ", user=" + user +
                ", numOfShowAd=" + numOfShowAd +
                ", inFavoritUsers='" + inFavoritUsers + '\'' +
                ", isCheck=" + isCheck +
                ", city='" + city + '\'' +
                ", categoty='" + categoty + '\'' +
                ", premium=" + premium +
                '}';
    }
}
