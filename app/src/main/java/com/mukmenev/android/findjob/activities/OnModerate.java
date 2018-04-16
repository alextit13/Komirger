package com.mukmenev.android.findjob.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.adapters.BoxAdapter;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.User;
import com.mukmenev.android.findjob.classes.firebase.GET_ALL_LIST;
import com.mukmenev.android.findjob.interfaces.GettingAllAdsListener;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OnModerate extends AppCompatActivity implements GettingAllAdsListener{
    @BindView(R.id.list_on_moderate_ads)ListView list_on_moderate_ads;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_moderate);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        user = (User) getIntent().getSerializableExtra("user");
        GET_ALL_LIST _G = new GET_ALL_LIST(OnModerate.this,user);
        _G.uploadList();
    }
    @Override
    public void takeData(ArrayList<Ad> list) {
        if (list!=null&&list.size()!=0){
            list_on_moderate_ads.setAdapter(new BoxAdapter(OnModerate.this,list,user));
        }else{
            Toast.makeText(this,"На модерации нет объявлений",Toast.LENGTH_LONG).show();
        }
    }
}
