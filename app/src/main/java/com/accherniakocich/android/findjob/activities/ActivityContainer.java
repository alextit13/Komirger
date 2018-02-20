package com.accherniakocich.android.findjob.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.fragments.FragmentAllCompanys;
import com.accherniakocich.android.findjob.fragments.FragmentCategory;

public class ActivityContainer extends AppCompatActivity {

    private String mCategory;
    private FragmentCategory mFragmentCategory;
    private FragmentAllCompanys mFragmentAllCompanys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        mCategory = getIntent().getStringExtra("category");
        if (mCategory.equals("Все компании")){
            mFragmentAllCompanys = new FragmentAllCompanys();
            getFragmentManager().beginTransaction()
                    .add(R.id.container_for_category,mFragmentAllCompanys,"f_a_c")
                    .commit();
            mFragmentAllCompanys.mSetDataInFragment(ActivityContainer.this);
        }else{
            mFragmentCategory = new FragmentCategory();
            getFragmentManager().beginTransaction()
                    .add(R.id.container_for_category,mFragmentCategory,"f_c")
                    .commit();
            mFragmentCategory.mSetDataInFragment(ActivityContainer.this,mCategory);
        }
    }
}
