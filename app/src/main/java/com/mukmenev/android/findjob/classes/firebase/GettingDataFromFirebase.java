package com.mukmenev.android.findjob.classes.firebase;

import android.content.Context;

import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.square_otto.BusStation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GettingDataFromFirebase {
    private Context context;
    public void getListAds(Context c){
        context = c;
        ArrayList<Ad>list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    list.add(data.getValue(Ad.class));
                                }
                                pushDataList(list);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }

    public void pushDataList(ArrayList<Ad> list) {
        BusStation.getBus().register(context);
        BusStation.getBus().post(list);
    }
}
