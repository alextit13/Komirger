package com.accherniakocich.android.findjob.find;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
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
import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.enums.EnumCitiesRUSSIA;
import com.accherniakocich.android.findjob.enums.EnumForCategories;
import com.accherniakocich.android.findjob.enums.GENERATE_LISTS_CLASS;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;

public class Find extends AppCompatActivity {

    private RangeSeekBar rangeSeekBar;

    private AutoCompleteTextView edit_text_find;
    private Button premium, not_premium, button_find;
    private TextView minPrice, maxPrice;
    private CheckBox checkBox_only_with_photo;
    private Spinner spinner_category_find,spinner_city_find;
    private ArrayList<Ad>list;
    private ArrayList<Ad>list_result;
    private boolean premium_check = false;
    private User user;
    private GENERATE_LISTS_CLASS GLC;
    private String tP = "https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb";

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
                        completeAutoCompleteList();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void findLogic() {
        list_result = new ArrayList<>();
        if (minPrice.getText().toString().equals("")){
            minPrice.setText("0");
        }
        if (maxPrice.getText().toString().equals("")){
            maxPrice.setText("100000");
        }
        if (!edit_text_find.getText().toString().equals("")){

            for (int i = 0;i<list.size();i++){

                if (list.get(i).toString().contains(edit_text_find.getText().toString())){ // содержит ли заданное слово
                    Log.d(MainActivity.LOG_TAG,"содержит слово");
                    if (list.get(i).isPremium()==premium_check){ // премиум или нет
                        Log.d(MainActivity.LOG_TAG,"премиум совпал");
                        if (list.get(i).getCostAd()>=Integer.parseInt(minPrice.getText().toString())&&list.get(i).getCostAd()<=Integer.parseInt(maxPrice.getText().toString())){ // цена
                            Log.d(MainActivity.LOG_TAG,"цена сошлась");
                            if (checkBox_only_with_photo.isChecked()){ // с фото или без
                                Log.d(MainActivity.LOG_TAG,"С ФОТО");
                                if (!list.get(i).getImagePathAd_1().equals(tP)||!list.get(i).getImagePathAd_2().equals(tP)||!list.get(i).getImagePathAd_3().equals(tP)||!list.get(i).getImagePathAd_4().equals(tP)||!list.get(i).getImagePathAd_5().equals(tP)){
                                    Log.d(MainActivity.LOG_TAG,"фото не пустое");
                                    if (list.get(i).getCategoty().equals(spinner_category_find.getSelectedItem().toString())){
                                        Log.d(MainActivity.LOG_TAG,"категория совпала");
                                        if (list.get(i).getCity().contains(spinner_city_find.getSelectedItem().toString())){
                                            Log.d(MainActivity.LOG_TAG,"город совпал");
                                            list_result.add(list.get(i));
                                        }
                                    }
                                }
                            }else{
                                Log.d(MainActivity.LOG_TAG,"БЕЗ ФОТО");
                                if (list.get(i).getCategoty().equals(spinner_category_find.getSelectedItem().toString())){
                                    Log.d(MainActivity.LOG_TAG,"содержит слово");
                                    if (list.get(i).getCity().contains(spinner_city_find.getSelectedItem().toString())){
                                        Log.d(MainActivity.LOG_TAG,"содержит слово");
                                        list_result.add(list.get(i));
                                    }
                                }
                            }
                        }
                    }
                }

            }

            Intent intent = new Intent(Find.this, FindResults.class);
            intent.putExtra("user",user);
            intent.putExtra("results",list_result);
            startActivity(intent);
        }else{
            Snackbar.make(button_find,"Введите запрос", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    private void init() {
        GLC = new GENERATE_LISTS_CLASS();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        edit_text_find = (AutoCompleteTextView) findViewById(R.id.edit_text_find);

        premium = (Button) findViewById(R.id.premium);
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

    private void initSeekBar() {
        rangeSeekBar = (RangeSeekBar) findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                minPrice.setText(minValue.toString());
                maxPrice.setText(maxValue.toString());
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.premium:
                //
                changeBackgroundColorButtons(premium);
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
            // премиум
            premium.setBackgroundColor(getResources().getColor(R.color.green));
            not_premium.setBackgroundColor(getResources().getColor(R.color.blue));
            premium_check = true;
        }else if (button.getText().equals(getResources().getString(R.string.not_premium))){
            // не премиум
            premium.setBackgroundColor(getResources().getColor(R.color.blue));
            not_premium.setBackgroundColor(getResources().getColor(R.color.green));
            premium_check = false;
        }
    }

    private void completteCategory_spinner(){
        ArrayAdapter adapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item,
                GLC.getListCategories(Arrays.asList(EnumForCategories.values()),"ВСЕ КАТЕГОРИИ"));
        spinner_category_find.setAdapter(adapter);
    }

    private void completteCity_spinner(){
        ArrayAdapter adapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item,
                GLC.getListCitiesRussia(Arrays.asList(EnumCitiesRUSSIA.values()),"ВСЕ ГОРОДА"));
        spinner_city_find.setAdapter(adapter);
    }

    private void completeAutoCompleteList(){
        ArrayList<String>listTitles = getList();
        ArrayAdapter adapter = new ArrayAdapter(Find.this,android.R.layout.simple_list_item_1,listTitles);
        edit_text_find.setAdapter(adapter);
    }

    private ArrayList<String> getList() {
        ArrayList<String>l = new ArrayList<>();
        for (int i = 0; i<list.size();i++){
            l.add(list.get(i).getNameJobAd());
        }
        return l;
    }
}
