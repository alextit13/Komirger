package com.mukmenev.android.findjob.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.adapters.CompaniesAdapter;
import com.mukmenev.android.findjob.classes.Company;
import com.mukmenev.android.findjob.classes.TakeDataFromFirebase;
import com.mukmenev.android.findjob.classes.square_otto.BusStation;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;

import butterknife.BindView;

public class FragmentAllCompanys extends Fragment{
    private Context mContext;
    private View view;
    private LinearLayout mConstraintLayout;
    private CompaniesAdapter mAdapter;
    @BindView(R.id.list_category)ListView mListCategory;
    @Override
    public void onStart() {
        super.onStart();
        BusStation.getBus().register(this);
    }
    @Override
    public void onDestroy() {
        BusStation.getBus().unregister(this);
        super.onDestroy();
    }
    public void mSetDataInFragment(Context c){
        mContext=c;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category,container,false);
        mListCategory = (ListView)view.findViewById(R.id.list_category);
        ((FloatingActionButton) view.findViewById(R.id.find_companies))
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickFloatingActionButton();
                            }
                        }
                );
        mGetListCompany("");
        return view;
    }
    private void mGetListCompany(String filter) {
        TakeDataFromFirebase mTakeDataFromServer = new TakeDataFromFirebase();
        mTakeDataFromServer.getFromServerCompanise(filter);
    }
    @Subscribe
    public void mGettingListWithCompanies(ArrayList<Company>LC){
        if (LC==null||LC.size()==0){
            mListCategory.setAdapter(null);
        }else{
            mAdapter = new CompaniesAdapter(mContext,LC);
            mListCategory.setAdapter(mAdapter);
        }
    }
    void onClickFloatingActionButton(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("Поиск");
        mConstraintLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.find_dialog,null);
        dialog.setView(mConstraintLayout);
        dialog.setPositiveButton("Поиск",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        find(((EditText)mConstraintLayout.findViewById(R.id.entered_word_find_companies))
                                .getText().toString());
                        dialog.dismiss();
                    }
                });
        dialog.create().show();
    }
    private void find(String s) {
        mGetListCompany(s);
        mAdapter.notifyDataSetChanged();
    }
}