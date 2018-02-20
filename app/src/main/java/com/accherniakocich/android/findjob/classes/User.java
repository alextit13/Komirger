package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class User implements Serializable{
    private String email;
    private String nickName;
    private String name;
    private String image_path;


    public User() {

    }

    public User(String email, String nickName, String name, String image_path) {
        this.email = email;
        this.nickName = nickName;
        this.name = name;
        this.image_path = image_path;
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
