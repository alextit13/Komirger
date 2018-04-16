package com.mukmenev.android.findjob.fragments.find_fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.Ad;

import java.util.ArrayList;

public class FragmentFindResult extends Fragment{

    private Context context;
    private ArrayList<Ad>listResult;

    public void setContextFragment(Context c, ArrayList<Ad>list){
        listResult = list;
        context = c;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_result,container,false);
        ListView list_view_fragment_find_result = (ListView)view.findViewById(R.id.list_view_fragment_find_result);
        ArrayList<String>listStrings = new ArrayList<>();
        for (int i = 0; i<listResult.size();i++){
            listStrings.add(listResult.get(i).getNameJobAd());
        }
        ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,listStrings);
        list_view_fragment_find_result.setAdapter(adapter);
        return view;
    }
}