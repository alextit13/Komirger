package com.mukmenev.android.findjob.admin.classes;

import android.app.Fragment;
import com.mukmenev.android.findjob.admin.interfaces.GetDataFromFirebase;
import com.mukmenev.android.findjob.classes.Ad;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetListAds {
    private Fragment fragment;
    private ArrayList<Ad>list = new ArrayList<>();
    private GetDataFromFirebase mGetData;

    public GetListAds(Fragment fragment) {
        this.fragment = fragment;
    }

    public void getList() {
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    if (!data.getValue(Ad.class).isCheck()){
                                        list.add(data.getValue(Ad.class));
                                    }
                                }
                                mGetData = (GetDataFromFirebase) fragment;
                                mGetData.dataList(list);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }
}
