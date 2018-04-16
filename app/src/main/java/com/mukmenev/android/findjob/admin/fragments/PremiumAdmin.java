package com.mukmenev.android.findjob.admin.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.admin.classes.PremAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PremiumAdmin extends Fragment {
    @BindView(R.id.name_ad_admin)EditText name_ad_admin;
    @BindView(R.id.rewiew_ad_premium_admin)EditText rewiew_ad_premium_admin;
    @BindView(R.id.add_premium_ad)Button add_premium_ad;
    private String name,about;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_premium,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.add_premium_ad)void adPremium(Button button){
        if (checkCompleteFields()){
            name = name_ad_admin.getText().toString();
            about = rewiew_ad_premium_admin.getText().toString();
            goToFirebase();
        }
    }

    private void goToFirebase() {
        PremAd ad = new PremAd(name,about,new Date().getTime()+"");
        FirebaseDatabase.getInstance().getReference().child("ads_prem")
                .child(ad.getDate())
                .setValue(ad)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                cleaner();
                            }
                        }
                );
    }

    private void cleaner() {
        name_ad_admin.setText("");
        rewiew_ad_premium_admin.setText("");
    }

    private boolean checkCompleteFields() {
        boolean check = false;
        if (!name_ad_admin.getText().toString().equals("")
                &&!rewiew_ad_premium_admin.getText().toString().equals("")){
            check = true;
        }else{
            check = false;
        }
        return check;
    }
}
