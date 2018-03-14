package com.accherniakocich.android.findjob.fragments.find_fragments;

import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.FindQuestionParameters;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.firebase.TakeDataForFind;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.accherniakocich.android.findjob.classes.square_otto.Event;
import com.accherniakocich.android.findjob.enums.EnumCitiesRUSSIA;
import com.accherniakocich.android.findjob.enums.EnumForCategories;
import com.accherniakocich.android.findjob.enums.GENERATE_LISTS_CLASS;
import com.accherniakocich.android.findjob.find.FindLogic;
import com.accherniakocich.android.findjob.interfaces.ReciveFindResultData;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentFind extends Fragment{
    private Activity activity;
    @BindView(R.id.frame_layout_find)FrameLayout mFrameLayout;
    @BindView(R.id.edit_text_find)AutoCompleteTextView edit_text_find;
    @BindView(R.id.premium)Button premium;
    @BindView(R.id.not_premium)Button not_premium;
    @BindView(R.id.rangeSeekBar)CrystalRangeSeekbar rangeSeekBar;
    @BindView(R.id.checkBox_only_with_photo)CheckBox checkBox_only_with_photo;
    @BindView(R.id.spinner_category_find)Spinner spinner_category_find;
    @BindView(R.id.spinner_city_find)Spinner spinner_city_find;
    @BindView(R.id.price_from)TextView price_from;
    @BindView(R.id.price_to)TextView price_to;

    private int swich = 0;
    private GENERATE_LISTS_CLASS _GLT;
    private ReciveFindResultData _mRecieveInterface;
    private ArrayList<Ad>list = new ArrayList<>();
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        ButterKnife.bind(this, view);
        BusStation.getBus().register(this);
        setPrice();
        TakeDataForFind data = new TakeDataForFind();
        _GLT = new GENERATE_LISTS_CLASS();
        data.initialization();
        completeCategories();
        completeCities();
        return view;
    }

    private void setPrice() {
        rangeSeekBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                price_from.setText(minValue.intValue()+"");
                price_to.setText(maxValue.intValue()+"");
                if(maxValue.intValue()>=9999){
                    price_to.setText("~");
                }
            }
        });
    }

    private void completeCategories() {
        ArrayAdapter mAdapterCategories = new ArrayAdapter(activity,android.R.layout.simple_list_item_1,
                _GLT.getListCategories(Arrays.asList(EnumForCategories.values()),"ВСЕ КАТЕГОРИИ"));
        spinner_category_find.setAdapter(mAdapterCategories);
    }

    private void completeCities() {
        ArrayAdapter mAdapterCities = new ArrayAdapter (activity,android.R.layout.simple_spinner_item,
                _GLT.getListCitiesRussia(Arrays.asList(EnumCitiesRUSSIA.values()),"ВСЕ ГОРОДА"));
        spinner_city_find.setAdapter(mAdapterCities);
        _GLT = null;
    }

    @Subscribe
    public void mOnCompleteAutocompleteEtitText(Event event){
        ArrayAdapter mAdapter = new ArrayAdapter(activity,android.R.layout.simple_list_item_1,event.getProduct());
        edit_text_find.setAdapter(mAdapter);
    }

    public void mFragmentConstructor(Activity a, ArrayList<Ad> l, User u){
        user = u;
        list = l;
        activity = a;
    }

    @OnClick(R.id.premium)public void bullonPrivateClick(Button mPremiumButton){
        clickPremium(mPremiumButton.getId());
    }

    @OnClick(R.id.not_premium)public void bullonNonPrivateClick(Button mNotPremiumButton){
        clickPremium(mNotPremiumButton.getId());
    }

    private void clickPremium(int id) {
        switch (id){
            case R.id.premium:
                if (swich==1){
                    swich=0;
                    premium.animate().setStartDelay(100).scaleX(1f).scaleY(1f).start();
                    not_premium.animate().setStartDelay(100).scaleX(1f).scaleY(1f).start();
                    premium.setBackgroundColor(Color.parseColor("#215d99"));
                    not_premium.setBackgroundColor(Color.parseColor("#215d99"));
                }else{
                    premium.animate().setStartDelay(100).scaleX(.8f).scaleY(.8f).start();
                    not_premium.animate().setStartDelay(100).scaleX(1f).scaleY(1f).start();
                    premium.setBackgroundColor(Color.parseColor("#678C32"));
                    not_premium.setBackgroundColor(Color.parseColor("#215d99"));
                    swich = 1;
                }
                break;
            case R.id.not_premium:
                if (swich==2){
                    swich=0;
                    premium.animate().setStartDelay(100).scaleX(1f).scaleY(1f).start();
                    not_premium.animate().setStartDelay(100).scaleX(1f).scaleY(1f).start();
                    premium.setBackgroundColor(Color.parseColor("#215d99"));
                    not_premium.setBackgroundColor(Color.parseColor("#215d99"));
                }else{
                    premium.animate().setStartDelay(100).scaleX(1).scaleY(1f).start();
                    not_premium.animate().setStartDelay(100).scaleX(.8f).scaleY(.8f).start();
                    premium.setBackgroundColor(Color.parseColor("#215d99"));
                    not_premium.setBackgroundColor(Color.parseColor("#678C32"));
                    swich = 2;
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.button_find) public void find(Button mButtonFind){
        // TODO тут передаем уже отсортированный результат в виде листа с Объявлениями
        // TODO(RealmDatabase) в родительское активити и потом с активити передаем в другой фрагмент - FragmentResult
        getFindLogic();
    }
    @Subscribe
    public void getSortListAds(ArrayList<Ad>list){
        _mRecieveInterface.getListAds(list);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _mRecieveInterface = (ReciveFindResultData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    private void getFindLogic() {
        boolean premium = false;
        boolean photo = false;
        if (swich==1){
            premium = true;
        }else if (swich==2){
            premium = false;
        }else if (swich==0){
            premium=false;
        }
        if (checkBox_only_with_photo.isChecked()){
            photo = true;
        }

        int price_to_int = 0;
        if (price_to.getText().toString().equals("~")){
            price_to_int = 2147483646;
        }else{
            price_to_int = Integer.parseInt(price_to.getText().toString());
        }
        FindQuestionParameters _m_FqP = new FindQuestionParameters(
                edit_text_find.getText().toString()
                ,premium
                ,Integer.parseInt(price_from.getText().toString())
                ,price_to_int
                ,photo
                ,spinner_category_find.getSelectedItem().toString()
                ,spinner_city_find.getSelectedItem().toString()
        );

        FindLogic _mFindLogic = new FindLogic();
        _mFindLogic.toFindQuestionParameters(_m_FqP,activity,list,user);
    }
}