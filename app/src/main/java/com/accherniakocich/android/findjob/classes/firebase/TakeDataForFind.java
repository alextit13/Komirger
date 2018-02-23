package com.accherniakocich.android.findjob.classes.firebase;

import android.util.Log;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.accherniakocich.android.findjob.classes.square_otto.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class TakeDataForFind {
    public void initialization(){
        BusStation.getBus().register(this);
        ArrayList<String>list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    list.add(data.getValue(Ad.class).getNameJobAd());
                                }
                                recieveData(new Event(list));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }

    private void recieveData(Event event){
        BusStation.getBus().post(event);
    }
}
