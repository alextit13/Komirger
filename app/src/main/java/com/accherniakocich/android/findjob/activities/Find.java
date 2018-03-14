package com.accherniakocich.android.findjob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.fragments.find_fragments.FragmentFind;
import com.accherniakocich.android.findjob.fragments.find_fragments.FragmentFindResult;
import com.accherniakocich.android.findjob.interfaces.ReciveFindResultData;

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
        mFragmentFind.mFragmentConstructor(Find.this);
    }

    @Override
    public void getListAds(ArrayList<Ad> listresultAds) {
        Intent intent = new Intent(Find.this,FindResult.class);
        intent.putExtra("list",listresultAds);
        startActivity(intent);
        /*FragmentFindResult mFragmentFindResult = new FragmentFindResult();
        getFragmentManager().beginTransaction()
                .replace(R.id.find_container,mFragmentFindResult)
                .commit();
        mFragmentFindResult.setContextFragment(Find.this,listresultAds);*/
    }
}
