package com.mukmenev.android.findjob.admin.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.activities.Details;
import com.mukmenev.android.findjob.adapters.BoxAdapter;
import com.mukmenev.android.findjob.admin.classes.GetListAds;
import com.mukmenev.android.findjob.admin.interfaces.GetDataFromFirebase;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        if (listAdNotCheck.size()>0){
            admin_list.setAdapter(new BoxAdapter(getActivity(),listAdNotCheck,null));
            clicker(listAdNotCheck);
        }
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
