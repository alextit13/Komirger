package com.accherniakocich.android.findjob.find.converter;

import com.accherniakocich.android.findjob.classes.Ad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JSON_Converter {
    public String convertToStringArray(ArrayList<Ad> listAds) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Ad>>() {
        }.getType();
        String listObjects = gson.toJson(listAds, type);
        return listObjects;
    }

    public ArrayList<Ad> convertToAdList(String list) {
        ArrayList<Ad> listAds = new ArrayList<>();
        Gson gson = new Gson();
        if (list != null) {
            Type type = new TypeToken<ArrayList<Ad>>() {
            }.getType();
            listAds = gson.fromJson(list, type);
        }
        return listAds;
    }
}
