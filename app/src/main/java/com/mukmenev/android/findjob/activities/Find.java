package com.mukmenev.android.findjob.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.User;
import com.mukmenev.android.findjob.fragments.find_fragments.FragmentFind;
import com.mukmenev.android.findjob.interfaces.ReciveFindResultData;

import java.util.ArrayList;

public class Find extends AppCompatActivity implements ReciveFindResultData{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        FragmentFind mFragmentFind = new FragmentFind();
        getFragmentManager().beginTransaction()
                .add(R.id.find_container,mFragmentFind,"mFragmentFind")
                .commit();
        mFragmentFind.mFragmentConstructor(Find.this,(ArrayList<Ad>) getIntent().getSerializableExtra("list")
        , (User)getIntent().getSerializableExtra("user"));
    }

    @Override
    public void getListAds(ArrayList<Ad> listresultAds) {




        /*FragmentFindResult mFragmentFindResult = new FragmentFindResult();
        getFragmentManager().beginTransaction()
                .replace(R.id.find_container,mFragmentFindResult)
                .commit();
        mFragmentFindResult.setContextFragment(Find.this,listresultAds);*/
    }
}
