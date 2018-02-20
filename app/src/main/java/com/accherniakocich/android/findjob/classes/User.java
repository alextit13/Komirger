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

    public User() {

    }

    public User(String email, String nickName, String name, String image_path, String about_me, int rating, boolean my_locate) {
        this.email = email;
        this.nickName = nickName;
        this.name = name;
        this.image_path = image_path;
        this.about_me = about_me;
        this.rating = rating;
        this.my_locate = my_locate;
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
}
