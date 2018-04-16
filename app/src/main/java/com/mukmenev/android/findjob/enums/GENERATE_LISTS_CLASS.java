package com.mukmenev.android.findjob.enums;

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
    public ArrayList<String>getListCitiesBelarus(List<EnumCitiesBELARUS>list,String header){
        list = Arrays.asList(EnumCitiesBELARUS.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesFrance(List<EnumCitiesFRANCE>list,String header){
        list = Arrays.asList(EnumCitiesFRANCE.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesGermany(List<EnumCitiesGERMANY>list,String header){
        list = Arrays.asList(EnumCitiesGERMANY.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesItalia(List<EnumCitiesITALY>list,String header){
        list = Arrays.asList(EnumCitiesITALY.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesKazakstan(List<EnumCitiesKAZAKSTAN>list,String header){
        list = Arrays.asList(EnumCitiesKAZAKSTAN.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesKirgizstan(List<EnumCitiesKIRGISTAN>list,String header){
        list = Arrays.asList(EnumCitiesKIRGISTAN.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesEspaniolla(List<EnumCitiesSPAIN>list,String header){
        list = Arrays.asList(EnumCitiesSPAIN.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesTadjikistan(List<EnumCitiesTADJIKISTAN>list,String header){
        list = Arrays.asList(EnumCitiesTADJIKISTAN.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesUkraina(List<EnumCitiesUKRAIN>list,String header){
        list = Arrays.asList(EnumCitiesUKRAIN.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
    public ArrayList<String>getListCitiesUzbekistan(List<EnumCitiesUZBEKISTAN>list,String header){
        list = Arrays.asList(EnumCitiesUZBEKISTAN.values());
        ArrayList<String>L = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            L.add(list.get(i).name());
        }
        Collections.sort(L.subList(1, list.size()));
        L.add(0,header);
        return L;
    }
}
