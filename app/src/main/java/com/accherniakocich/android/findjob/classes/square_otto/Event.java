package com.accherniakocich.android.findjob.classes.square_otto;

import java.util.ArrayList;

public class Event {
    ArrayList<String> product;

    public Event(ArrayList<String> product) {
        this.product = product;
    }

    public ArrayList<String> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<String> product) {
        this.product = product;
    }
}
