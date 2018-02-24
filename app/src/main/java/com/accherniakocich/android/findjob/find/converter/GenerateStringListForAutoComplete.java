package com.accherniakocich.android.findjob.find.converter;

import com.accherniakocich.android.findjob.classes.Ad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GenerateStringListForAutoComplete {
    public ArrayList<String> getStringList(ArrayList<Ad>list){
        ArrayList<String>mListString = new ArrayList<>(list.size());
        for (Ad ad : list){
            mListString.add(ad.getNameJobAd());
        }
        return mListString;
    }
}
