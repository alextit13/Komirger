package com.accherniakocich.android.findjob.firebase;

import android.app.Activity;
import android.content.Context;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.QuestionFind;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.accherniakocich.android.findjob.find.FindLogic;
import com.accherniakocich.android.findjob.interfaces.TestInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetDataFirebase {

    private Activity activity;

    public void getListAds(Activity a, QuestionFind question, User user) {
        activity = a;
        ArrayList<Ad> list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    list.add(data.getValue(Ad.class));
                                }
                                TestInterface in = (TestInterface) new FindLogic();
                                in.someEvent(activity,list,question,user);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }
}