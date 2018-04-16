package com.mukmenev.android.findjob.admin.classes;

import java.io.Serializable;

public class PremAd implements Serializable {
    private String name;
    private String about;
    private String date;

    public PremAd() {
    }

    public PremAd(String name, String about, String date) {
        this.name = name;
        this.about = about;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
