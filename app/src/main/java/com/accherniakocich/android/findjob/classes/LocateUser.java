package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;
import java.util.Map;

public class LocateUser implements Serializable{
    String keyNameUser;
    private Map<String,double[]>mapLocate;

    public LocateUser() {
    }

    public LocateUser(String keyNameUser, Map<String, double[]> mapLocate) {
        this.keyNameUser = keyNameUser;
        this.mapLocate = mapLocate;
    }

    public String getKeyNameUser() {
        return keyNameUser;
    }

    public void setKeyNameUser(String keyNameUser) {
        this.keyNameUser = keyNameUser;
    }

    public Map<String, double[]> getMapLocate() {
        return mapLocate;
    }

    public void setMapLocate(Map<String, double[]> mapLocate) {
        this.mapLocate = mapLocate;
    }

    public double getLon(){
        return mapLocate.get(keyNameUser)[0];
    }
    public double getLat(){
        return mapLocate.get(keyNameUser)[1];
    }
}
