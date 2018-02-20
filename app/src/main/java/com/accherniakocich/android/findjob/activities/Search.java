package com.accherniakocich.android.findjob.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.findResult.FindResults;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Search extends AppCompatActivity {

    private Spinner spinner_location, spinner_cost_from, spinner_cost_to, spinner_type_of_money;
    private ArrayList<String> list_city, list_cost_from, list_cost_to, list_type_money;
    private ArrayList<Ad> list_ads;
    private Button button_serch_cancel, button_serch_find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {
        button_serch_cancel = (Button) findViewById(R.id.button_serch_cancel);
        button_serch_find = (Button) findViewById(R.id.button_serch_find);
        list_ads = new ArrayList<>();

        spinner_location = (Spinner) findViewById(R.id.spinner_location);
        spinner_cost_from = (Spinner) findViewById(R.id.spinner_cost_from);
        spinner_cost_to = (Spinner) findViewById(R.id.spinner_cost_to);
        spinner_type_of_money = (Spinner) findViewById(R.id.spinner_type_of_money);

        list_city = new ArrayList<>();
        list_cost_from = new ArrayList<>();
        list_cost_to = new ArrayList<>();
        list_type_money = new ArrayList<>();

        list_type_money.add("$");
        list_type_money.add("р");
        list_type_money.add("€");

        list_cost_from.add("1");
        list_cost_from.add("500");
        list_cost_from.add("1000");
        list_cost_from.add("5000");
        list_cost_from.add("10000");
        list_cost_from.add("20000");
        list_cost_from.add("50000");
        list_cost_from.add("100000");
        list_cost_from.add("1000000");

        list_cost_to.add("1");
        list_cost_to.add("500");
        list_cost_to.add("1000");
        list_cost_to.add("5000");
        list_cost_to.add("10000");
        list_cost_to.add("20000");
        list_cost_to.add("50000");
        list_cost_to.add("100000");
        list_cost_to.add("1000000");

        generateLists();
    }

    private void generateLists() {
        FirebaseDatabase.getInstance().getReference().child("ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    list_ads.add(postSnapshot.getValue(Ad.class));
                    Log.d(MainActivity.LOG_TAG, "get");
                }

                for (int i = 0; i < list_ads.size(); i++) {
                    if (list_ads.get(i).isCheck()) {
                        list_city.add(list_ads.get(i).getCity());
                    }
                }
                Set<String> hs = new HashSet<>();
                hs.addAll(list_city);
                list_city.clear();
                list_city.addAll(hs);
                createAdapters();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createAdapters() {
        ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, list_city);
        ArrayAdapter<String> adapter_from = new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, list_cost_from);
        ArrayAdapter<String> adapter_to = new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, list_cost_to);
        ArrayAdapter<String> adapter_money = new ArrayAdapter<String>(Search.this, android.R.layout.simple_spinner_item, list_type_money);

        spinner_location.setAdapter(adapter_city);
        spinner_cost_from.setAdapter(adapter_from);
        spinner_cost_to.setAdapter(adapter_to);
        spinner_type_of_money.setAdapter(adapter_money);
    }

    public void onClickSearch(View view) {
        switch (view.getId()) {
            case R.id.button_serch_find:
                // find
                find();
                break;
            case R.id.button_serch_cancel:
                // cancel
                finish();
                break;
            default:
                break;

        }
    }

    private void find() {
        ArrayList<Ad>list_with_finder = new ArrayList<>();
        int from = Integer.parseInt(spinner_cost_from.getSelectedItem().toString());
        int to = Integer.parseInt(spinner_cost_to.getSelectedItem().toString());
        for (int i = 0;i<list_ads.size();i++){
            if (list_ads.get(i).getCity().equals(spinner_location.getSelectedItem())
                    &&
                    list_ads.get(i).getCostAd()>=from && list_ads.get(i).getCostAd()<=to){
                list_with_finder.add(list_ads.get(i));
            }
        }
        if (!list_with_finder.isEmpty()){
            // показываем результаты поиска
            Intent intent = new Intent(Search.this, FindResults.class);
            intent.putExtra("results",list_with_finder);
            startActivity(intent);
        }else{
            Snackbar.make(button_serch_find,"Ничего не найдено",Snackbar.LENGTH_SHORT).show();
        }
    }
}
