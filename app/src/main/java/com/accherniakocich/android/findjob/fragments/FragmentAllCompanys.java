package com.accherniakocich.android.findjob.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.CompaniesAdapter;
import com.accherniakocich.android.findjob.classes.Company;
import com.accherniakocich.android.findjob.classes.TakeDataFromFirebase;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;

public class FragmentAllCompanys extends Fragment{
    private Context mContext;
    private View view;
    private ArrayList<Company>listCompany;
    @BindView(R.id.list_category)ListView mListCategory;
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
    public void mSetDataInFragment(Context c){
        mContext=c;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category,container,false);
        mGetListCompany();
        return view;
    }

    private void mGetListCompany() {
        TakeDataFromFirebase mTakeDataFromServer = new TakeDataFromFirebase();
        mTakeDataFromServer.getFromServerCompanise();
    }
    @Subscribe
    public void mGettingListWithCompanies(ArrayList<Company>listCompany){
        CompaniesAdapter adapter = new CompaniesAdapter(mContext,listCompany);
        mListCategory.setAdapter(adapter);
    }
}