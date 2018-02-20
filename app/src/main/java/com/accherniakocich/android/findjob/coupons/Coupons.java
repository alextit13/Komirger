package com.accherniakocich.android.findjob.coupons;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.accherniakocich.android.findjob.R;

public class Coupons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
    }

    public void clickCoupons(View view) {
        switch (view.getId()){
            case R.id.get_sk:
                Snackbar.make((Button)findViewById(R.id.get_sk),"Функция в разработке", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }
}
