package com.accherniakocich.android.findjob.find;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.QuestionFind;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.converted_enums.ConverterEnum;
import com.accherniakocich.android.findjob.enums.EnumCitiesRussia;
import com.accherniakocich.android.findjob.enums.EnumForCategories;
import com.accherniakocich.android.findjob.find.converter.GenerateStringListForAutoComplete;
import com.accherniakocich.android.findjob.firebase.GetDataFirebase;
import com.github.guilhe.rangeseekbar.SeekBarRangedView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;

public class Find extends AppCompatActivity {

    private SeekBarRangedView rangeSeekBar;

    private AutoCompleteTextView edit_text_find;
    private Button premiumButton, not_premium, button_find;
    private TextView minPrice, maxPrice;
    private CheckBox checkBox_only_with_photo;
    private Spinner spinner_category_find,spinner_city_find;
    private ArrayList<Ad>list;
    private boolean premium_check = false;
    private User user;

    private int premium = 0;
    private int cost_from,cost_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        init();
        downloadListAds();
    }

    private void downloadListAds() {
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            list.add(postSnapshot.getValue(Ad.class));
                        }
                        completeAutoCompleter();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void findLogic() {
        GetDataFirebase g = new GetDataFirebase();
        g.getListAds(this,createQuestion(),user);
    }

    private QuestionFind createQuestion() {
        cost_from = (int)rangeSeekBar.getSelectedMinValue();
        cost_to = (int) rangeSeekBar.getSelectedMaxValue();
        if (rangeSeekBar.getSelectedMaxValue()>4900){
            cost_to = 2147483646;
        }
        if (checkBox_only_with_photo.isChecked()){
            premium_check = true;
        }
        return new QuestionFind(edit_text_find.getText().toString()
        ,premium
        ,cost_from
        ,cost_to
        ,premium_check
        ,spinner_category_find.getSelectedItem().toString()
        ,spinner_city_find.getSelectedItem().toString());
    }

    private void init() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        edit_text_find = (AutoCompleteTextView) findViewById(R.id.edit_text_find);
        premiumButton = (Button) findViewById(R.id.premium);
        not_premium = (Button) findViewById(R.id.not_premium);
        button_find = (Button) findViewById(R.id.button_find);
        minPrice = (TextView) findViewById(R.id.minPrice);
        maxPrice = (TextView) findViewById(R.id.maxPrice);
        checkBox_only_with_photo = (CheckBox) findViewById(R.id.checkBox_only_with_photo);
        spinner_category_find = (Spinner) findViewById(R.id.spinner_category_find);
        spinner_city_find = (Spinner) findViewById(R.id.spinner_city_find);

        initSeekBar();
        completteCategory_spinner();
        completteCity_spinner();
    }

    private void completeAutoCompleter() {
        GenerateStringListForAutoComplete mGenerator = new GenerateStringListForAutoComplete();
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mGenerator.getStringList(list));
        edit_text_find.setAdapter(adapter);
    }

    private void initSeekBar() {
        rangeSeekBar = (SeekBarRangedView) findViewById(R.id.rangeSeekBar);
        minPrice.setText("0");
        maxPrice.setText("~");
        rangeSeekBar.setOnSeekBarRangedChangeListener(
                new SeekBarRangedView.OnSeekBarRangedChangeListener() {
                    @Override
                    public void onChanged(SeekBarRangedView view, double minValue, double maxValue) {
                        minPrice.setText((int)minValue+"");
                        maxPrice.setText((int)maxValue+"");
                        if (maxValue>4900){
                            maxPrice.setText("~");
                        }
                    }

                    @Override
                    public void onChanging(SeekBarRangedView view, double minValue, double maxValue) {

                    }
                }
        );
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.premium:
                //
                changeBackgroundColorButtons(premiumButton);
                break;
            case R.id.not_premium:
                //
                changeBackgroundColorButtons(not_premium);
                break;
            case R.id.button_find:
                // find logic
                findLogic();
                break;
            default:
                break;
        }
    }

    private void changeBackgroundColorButtons(Button button) {
        if (button.getText().equals(getResources().getString(R.string.premium))){
            if (premium==1){
                premiumButton.setBackgroundColor(getResources().getColor(R.color.blue));
                //not_premium.setBackgroundColor(getResources().getColor(R.color.green));
                premium_check = false;
                premium = 0;
                button.animate().setDuration(200).scaleX(1f).scaleY(1f).start();
            }else{
                // премиум
                button.animate().setDuration(200).scaleX(.95f).scaleY(.95f).start();
                premium = 1;
                premiumButton.setBackgroundColor(getResources().getColor(R.color.green));
                not_premium.setBackgroundColor(getResources().getColor(R.color.blue));
                premium_check = true;

            }
        }else if (button.getText().equals(getResources().getString(R.string.not_premium))){
            if (premium==2){
                button.animate().setDuration(200).scaleX(1f).scaleY(1f).start();
                //premiumButton.setBackgroundColor(getResources().getColor(R.color.green));
                not_premium.setBackgroundColor(getResources().getColor(R.color.blue));
                premium_check = false;
                premium = 0;
            }else{
                button.animate().setDuration(200).scaleX(.95f).scaleY(.95f).start();
                premium = 2;
                premiumButton.setBackgroundColor(getResources().getColor(R.color.blue));
                not_premium.setBackgroundColor(getResources().getColor(R.color.green));
                premium_check = false;
            }
            // не премиум
        }
    }

    private void completteCategory_spinner(){
        ArrayList<String>list_category = new ArrayList<>();
        ConverterEnum converter = new ConverterEnum();
        list_category = converter.getListCategories(Arrays.asList(EnumForCategories.values()),"ВСЕ КАТЕГОРИИ");
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list_category);
        spinner_category_find.setAdapter(adapter);
    }

    private void completteCity_spinner(){
        ArrayList<String> cities = new ArrayList<>();
        ConverterEnum converter = new ConverterEnum();
        cities = converter.getListCitiesRussia(Arrays.asList(EnumCitiesRussia.values()),"ВСЕ ГОРОДА");
        ArrayAdapter<String>adapter_cities = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cities);
        spinner_city_find.setAdapter(adapter_cities);
    }
}
