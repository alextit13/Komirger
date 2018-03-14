package com.accherniakocich.android.findjob.find;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.adapters.BoxAdapter;
import com.accherniakocich.android.findjob.activities.Details;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;

import java.util.ArrayList;

public class FindResults extends AppCompatActivity {

    private GridView list_with_find_result;
    private ArrayList <Ad> list;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_results);

        init();

        list_with_find_result = (GridView) findViewById(R.id.list_with_find_result);
        Intent intent = getIntent();
        list = (ArrayList<Ad>) intent.getSerializableExtra("results");
        BoxAdapter adapter = new BoxAdapter(this,list,null);
        if (list.size()!=0){
            list_with_find_result.setAdapter(adapter);
        }else{
            Toast.makeText(this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
        }
        list_with_find_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_details = new Intent(FindResults.this, Details.class);
                intent_details.putExtra("ad",list.get(i));
                intent_details.putExtra("user", user);
                intent_details.putExtra("fromWhereIntent",1);
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }
}
