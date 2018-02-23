package com.accherniakocich.android.findjob.fragments.find_fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.FindQuestionParameters;
import com.accherniakocich.android.findjob.classes.firebase.TakeDataForFind;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.accherniakocich.android.findjob.classes.square_otto.Event;
import com.accherniakocich.android.findjob.enums.EnumCitiesRUSSIA;
import com.accherniakocich.android.findjob.enums.EnumForCategories;
import com.accherniakocich.android.findjob.enums.GENERATE_LISTS_CLASS;
import com.accherniakocich.android.findjob.find.FindLogic;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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