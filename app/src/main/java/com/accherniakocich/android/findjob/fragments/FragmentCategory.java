package com.accherniakocich.android.findjob.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.BoxAdapter;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.TakeDataFromFirebase;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.UserSingleton;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

public class FragmentCategory extends Fragment{
    private Context mContext;
    private String mCategory;
    private ArrayList<Ad>listAd;
    private View view;
    private ListView listView;
    private BoxAdapter mAdapter;
    @Override
    public void onStart() {
        super.onStart();
        BusStation.getBus().register(this);
    }
    @Override
    public void onDestroy() {
        BusStation.getBus().unregister(this);
        super.onDestroy();
    }
    public void mSetDataInFragment(Context c, String category){
        mContext=c;
        mCategory=category;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category,container,false);
        listView = (ListView)view.findViewById(R.id.list_category);
        getList();
        listView
                .setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                goToDetails();
                            }
                        }
                );
        return view;
    }

    private void goToDetails() {
        User user = UserSingleton.user;

    }

    private void getList() {
        TakeDataFromFirebase mTakeDataFromFirebase = new TakeDataFromFirebase();
        mTakeDataFromFirebase.getFromServer(mCategory);
    }
    @Subscribe
    public void mGettingListResult(ArrayList<Ad> list){
        listAd=list;
        mAdapter = new BoxAdapter(mContext,listAd,null);
        listView.setAdapter(mAdapter);
    }
}