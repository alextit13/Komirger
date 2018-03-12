package com.accherniakocich.android.findjob.classes.firebase;

import android.app.Activity;
import android.content.Context;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.interfaces.GettingAllAdsListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class GET_ALL_LIST {
    private Activity activity;
    private User user;
    private ArrayList<Ad>list = new ArrayList<>();
    private GettingAllAdsListener listener;
    public GET_ALL_LIST(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

    public void uploadList(){
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    Ad ad = data.getValue(Ad.class);
                                    if (ad.getUser().getEmail().equals(user.getEmail())){
                                        if (!ad.isCheck()) list.add(ad);
                                    }
                                }
                                listener = (GettingAllAdsListener)activity;
                                listener.takeData(list);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }
}
