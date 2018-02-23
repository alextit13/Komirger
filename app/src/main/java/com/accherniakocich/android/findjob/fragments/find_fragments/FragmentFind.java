package com.accherniakocich.android.findjob.fragments.find_fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.firebase.TakeDataForFind;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.accherniakocich.android.findjob.classes.square_otto.Event;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.squareup.otto.Subscribe;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentFind extends Fragment{
    private Context context;
    @BindView(R.id.frame_layout_find)FrameLayout mFrameLayout;
    @BindView(R.id.edit_text_find)AutoCompleteTextView edit_text_find;
    @BindView(R.id.premium)Button premium;
    @BindView(R.id.not_premium)Button not_premium;
    @BindView(R.id.rangeSeekBar)CrystalRangeSeekbar rangeSeekBar;
    @BindView(R.id.checkBox_only_with_photo)CheckBox checkBox_only_with_photo;
    @BindView(R.id.spinner_category_find)Spinner spinner_category_find;
    @BindView(R.id.spinner_city_find)Spinner spinner_city_find;
    private int swich = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        ButterKnife.bind(this, view);
        BusStation.getBus().register(this);
        TakeDataForFind data = new TakeDataForFind();
        data.initialization();
        return view;
    }
    @Subscribe
    public void mOnCompleteAutocompleteEtitText(Event event){
        ArrayAdapter mAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,event.getProduct());
        edit_text_find.setAdapter(mAdapter);
    }

    public void mFragmentConstructor(Context c){
        context = c;
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
        // TODO(Ad) в родительское активити и потом с активити передаем в другой фрагмент - FragmentResult

    }

}