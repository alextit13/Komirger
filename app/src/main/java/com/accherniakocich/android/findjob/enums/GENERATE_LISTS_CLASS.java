package com.accherniakocich.android.findjob.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GENERATE_LISTS_CLASS {
    public ArrayList<String> getListCategories(List<EnumForCategories>list, String header){
        list = Arrays.asList(EnumForCategories.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesRussia(List<EnumCitiesRUSSIA>list,String header){
        list = Arrays.asList(EnumCitiesRUSSIA.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
}
