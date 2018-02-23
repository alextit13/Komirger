package com.accherniakocich.android.findjob.find;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.fragments.find_fragments.FragmentFind;

import butterknife.BindView;

public class Find extends AppCompatActivity {
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
}
