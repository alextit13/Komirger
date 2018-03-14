package com.accherniakocich.android.findjob.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.BoxAdapter;
import com.accherniakocich.android.findjob.classes.Ad;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindResult extends AppCompatActivity {

    @BindView(R.id.list_find_result)ListView mListResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);
        ButterKnife.bind(this);
        getAdapter();
    }

    private void getAdapter() {
        mListResult.setAdapter(
                new BoxAdapter(
                        this,(ArrayList<Ad>) getIntent().getSerializableExtra("list")
                )
        );
    }
}
