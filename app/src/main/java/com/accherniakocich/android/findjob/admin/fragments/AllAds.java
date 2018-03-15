package com.accherniakocich.android.findjob.admin.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.Details;
import com.accherniakocich.android.findjob.adapters.BoxAdapter;
import com.accherniakocich.android.findjob.admin.AdminPanel;
import com.accherniakocich.android.findjob.admin.classes.GetListAds;
import com.accherniakocich.android.findjob.admin.classes.PremAd;
import com.accherniakocich.android.findjob.admin.interfaces.GetDataFromFirebase;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllAds extends Fragment implements GetDataFromFirebase{

    @BindView(R.id.admin_list)ListView admin_list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_ads_admin,container,false);
        ButterKnife.bind(this,view);
        getData();
        return view;
    }

    private void getData() {
        GetListAds mGetList = new GetListAds(this);
        mGetList.getList();
    }
    // TODO stack from class through the interfaces is located low
    @Override
    public void dataList(ArrayList<Ad> listAdNotCheck) {
        admin_list.setAdapter(new BoxAdapter(getActivity(),listAdNotCheck,null));
        clicker(listAdNotCheck);
    }

    private void clicker(ArrayList<Ad>list) {
        admin_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), Details.class);
                        intent.putExtra("ad",list.get(position));
                        intent.putExtra("user",new User("admin@admin.com","admin","admin","","",0,false,1,false));
                        intent.putExtra("fromWhereIntent",3);
                        startActivity(intent);
                    }
                }
        );

    }
}
