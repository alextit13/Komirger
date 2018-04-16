package com.mukmenev.android.findjob.activities.log_and_reg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.fragments.RegistrationFragments;

public class ActivityContainerReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_reg);
        RegistrationFragments mRegistrationFragment = new RegistrationFragments();
        getFragmentManager().beginTransaction()
                .add(R.id.registration_container_yur,mRegistrationFragment,"reg_frag_comp")
                .commit();
        mRegistrationFragment.mGetData(ActivityContainerReg.this);
    }
}
