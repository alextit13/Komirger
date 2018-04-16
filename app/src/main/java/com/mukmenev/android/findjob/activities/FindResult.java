package com.mukmenev.android.findjob.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.adapters.BoxAdapter;
import com.mukmenev.android.findjob.classes.Ad;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindResult extends AppCompatActivity{

    @BindView(R.id.list_find_result)ListView mListResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);
        ButterKnife.bind(this);
        someEvent();

    }

    public void someEvent() {
        if (((ArrayList<Ad>) getIntent().getSerializableExtra("list")).size()!=0){
            mListResult.setAdapter(
                    new BoxAdapter(
                            this,(ArrayList<Ad>) getIntent().getSerializableExtra("list")
                            ,null
                    )
            );
            mListResult.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(FindResult.this,Details.class);
                            intent.putExtra("ad",((ArrayList<Ad>) getIntent().getSerializableExtra("list")).get(position));
                            intent.putExtra("user",getIntent().getSerializableExtra("user"));
                            startActivity(intent);
                        }
                    }
            );
        }else{
            Toast.makeText(this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
        }
    }
}
