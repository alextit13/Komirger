package com.mukmenev.android.findjob.classes.square_otto;

import com.mukmenev.android.findjob.classes.Ad;

import java.util.ArrayList;

public class EventAds {
    ArrayList<Ad> product;

    public EventAds(ArrayList<Ad> product) {
        this.product = product;
    }

    public ArrayList<Ad> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Ad> product) {
        this.product = product;
    }
}
