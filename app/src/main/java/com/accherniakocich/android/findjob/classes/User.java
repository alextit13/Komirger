package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class User implements Serializable{
    private String email;
    private String nickName;
    private String name;
    private String image_path;
    private String about_me;
    private int rating;
    private boolean my_locate;
    private int blocked;
    private boolean premium;

    public User() {
    }

    public User(String email, String nickName, String name, String image_path, String about_me, int rating, boolean my_locate, int blocked, boolean premium) {
        this.email = email;
        this.nickName = nickName;
        this.name = name;
        this.image_path = image_path;
        this.about_me = about_me;
        this.rating = rating;
        this.my_locate = my_locate;
        this.blocked = blocked;
        this.premium = premium;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public boolean isMy_locate() {
        return my_locate;
    }

    public void setMy_locate(boolean my_locate) {
        this.my_locate = my_locate;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", image_path='" + image_path + '\'' +
                ", about_me='" + about_me + '\'' +
                ", rating=" + rating +
                ", my_locate=" + my_locate +
                ", blocked=" + blocked +
                '}';
    }
}
