package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class Admin implements Serializable{
    String email;
    String name;
    String time;

    public Admin() {
    }

    public Admin(String email, String name, String time) {
        this.email = email;
        this.name = name;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
