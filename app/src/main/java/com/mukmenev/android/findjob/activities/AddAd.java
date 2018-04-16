package com.mukmenev.android.findjob.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.activities.log_and_reg.MainActivity;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.User;
import com.mukmenev.android.findjob.enums.EnumCitiesBELARUS;
import com.mukmenev.android.findjob.enums.EnumCitiesFRANCE;
import com.mukmenev.android.findjob.enums.EnumCitiesGERMANY;
import com.mukmenev.android.findjob.enums.EnumCitiesITALY;
import com.mukmenev.android.findjob.enums.EnumCitiesKAZAKSTAN;
import com.mukmenev.android.findjob.enums.EnumCitiesKIRGISTAN;
import com.mukmenev.android.findjob.enums.EnumCitiesRUSSIA;
import com.mukmenev.android.findjob.enums.EnumCitiesSPAIN;
import com.mukmenev.android.findjob.enums.EnumCitiesTADJIKISTAN;
import com.mukmenev.android.findjob.enums.EnumCitiesUKRAIN;
import com.mukmenev.android.findjob.enums.EnumCitiesUZBEKISTAN;
import com.mukmenev.android.findjob.enums.EnumForCategories;
import com.mukmenev.android.findjob.enums.GENERATE_LISTS_CLASS;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddAd extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 500;
    public static final int PICK_IMAGE_REQUEST_AD = 72;
    private User user;
    private StorageReference storageRef;
    private ImageView image_ad_1, image_ad_2, image_ad_3, image_ad_4, image_ad_5;
    private EditText edit_text_name_ad, edit_text_cost_job, edit_text_contacts_ad,
            edit_text_about_job_ad, consistance_auto, marka_auto, model_auto, type_of_body, colot,
            region, category, year_production_car, how_much_completed_road, type_of_engine, value_engine, transmission,color;
    private CheckBox cb_obmen, rassrochka;
    private Spinner spinner_type_money, spinner_category, spinner_city,spinner_country;
    private Button button_cancel_ad, button_add_ad;
    private ProgressBar progress_bar_ad;
    private FrameLayout container_ad_frame_layout;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Bitmap photo_1, photo_2, photo_3, photo_4, photo_5;
    private Ad ad, adFromEditDetail;
    private Uri photoURI_1, photoURI_2, photoURI_3, photoURI_4, photoURI_5, downloadUrl;
    private String mCurrentPhotoPath, urlPathPhoto_1, urlPathPhoto_2, urlPathPhoto_3, urlPathPhoto_4, urlPathPhoto_5;
    private int RETURNED_PHOTO = 0;
    private int iterator = 0;
    int iterator_sucsess = 0;
    private GENERATE_LISTS_CLASS _GLK;
    private LinearLayout car_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        init();
    }

    private void init() {
        spinner_country = (Spinner)findViewById(R.id.spinner_country);
        completeSpinnerCountry();
        color = (EditText) findViewById(R.id.color);
        consistance_auto = (EditText) findViewById(R.id.consistance_auto);
        marka_auto = (EditText) findViewById(R.id.marka_auto);
        model_auto = (EditText) findViewById(R.id.model_auto);
        type_of_body = (EditText) findViewById(R.id.type_of_body);
        colot = (EditText) findViewById(R.id.color);
        region = (EditText) findViewById(R.id.region);
        category = (EditText) findViewById(R.id.category);
        year_production_car = (EditText) findViewById(R.id.year_production_car);
        how_much_completed_road = (EditText) findViewById(R.id.how_much_completed_road);
        type_of_engine = (EditText) findViewById(R.id.type_of_engine);
        value_engine = (EditText) findViewById(R.id.value_engine);
        transmission = (EditText) findViewById(R.id.transmission);

        cb_obmen = (CheckBox) findViewById(R.id.cb_obmen);
        rassrochka = (CheckBox) findViewById(R.id.rassrochka);


        car_container = (LinearLayout) findViewById(R.id.car_container);
        image_ad_1 = (ImageView) findViewById(R.id.imageAd_1);
        image_ad_2 = (ImageView) findViewById(R.id.imageAd_2);
        image_ad_3 = (ImageView) findViewById(R.id.imageAd_3);
        image_ad_4 = (ImageView) findViewById(R.id.imageAd_4);
        image_ad_5 = (ImageView) findViewById(R.id.imageAd_5);
        spinner_category = (Spinner) findViewById(R.id.spinner_category);
        completeSpinnerCategory();
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        storageRef = FirebaseStorage.getInstance().getReference();
        edit_text_name_ad = (EditText) findViewById(R.id.name_ad);
        edit_text_cost_job = (EditText) findViewById(R.id.cost_job);
        edit_text_contacts_ad = (EditText) findViewById(R.id.contacts_ad);
        edit_text_about_job_ad = (EditText) findViewById(R.id.about_job);
        spinner_type_money = (Spinner) findViewById(R.id.spinner_add_ad);
        spinner_type_money.setAdapter(generateAdapter(generateListForSpnner()));
        button_cancel_ad = (Button) findViewById(R.id.cancel_button_ad);
        button_add_ad = (Button) findViewById(R.id.add_ad_button);
        container_ad_frame_layout = (FrameLayout) findViewById(R.id.container_ad_frame_layout);
        progress_bar_ad = (ProgressBar) findViewById(R.id.progress_bar_ad);
        progress_bar_ad.setVisibility(View.INVISIBLE);
        int request = 0;
        try {
            request = intent.getIntExtra("request", 0);
        } catch (Exception e) {
            Log.d(MainActivity.LOG_TAG, "Exception");
        }
        if (request != 0) {
            adFromEditDetail = (Ad) intent.getSerializableExtra("ad");
            User userFromEditDetail = (User) intent.getSerializableExtra("user");
            edit_text_name_ad.setText(userFromEditDetail.getNickName());
            edit_text_cost_job.setText(adFromEditDetail.getCostAd() + "");
            edit_text_about_job_ad.setText(adFromEditDetail.getTextAd());
            edit_text_contacts_ad.setText(adFromEditDetail.getPeopleSourceAd() + "");
        }
    }

    private void completeSpinnerCountry() {
        ArrayList<String>list = new ArrayList<>();
        list.add("Россия");
        list.add("Беларусь");
        list.add("Франция");
        list.add("Германия");
        list.add("Италия");
        list.add("Казахстан");
        list.add("Кыргызстан");
        list.add("Испания");
        list.add("Таджикистан");
        list.add("Украина");
        list.add("Узбекистан");
        spinner_country.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (list.get(position)){
                    case "Россия":
                        //
                        completeSpinnerCity(_GLK.getListCitiesRussia(Arrays.asList(EnumCitiesRUSSIA.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Беларусь":
                        //
                        completeSpinnerCity(_GLK.getListCitiesBelarus(Arrays.asList(EnumCitiesBELARUS.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Франция":
                        //
                        completeSpinnerCity(_GLK.getListCitiesFrance(Arrays.asList(EnumCitiesFRANCE.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Германия":
                        //
                        completeSpinnerCity(_GLK.getListCitiesGermany(Arrays.asList(EnumCitiesGERMANY.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Италия":
                        //
                        completeSpinnerCity(_GLK.getListCitiesItalia(Arrays.asList(EnumCitiesITALY.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Казахстан":
                        //
                        completeSpinnerCity(_GLK.getListCitiesKazakstan(Arrays.asList(EnumCitiesKAZAKSTAN.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Кыргызстан":
                        //
                        completeSpinnerCity(_GLK.getListCitiesKirgizstan(Arrays.asList(EnumCitiesKIRGISTAN.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Испания":
                        //
                        completeSpinnerCity(_GLK.getListCitiesEspaniolla(Arrays.asList(EnumCitiesSPAIN.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Таджикистан":
                        //
                        completeSpinnerCity(_GLK.getListCitiesTadjikistan(Arrays.asList(EnumCitiesTADJIKISTAN.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Украина":
                        //
                        completeSpinnerCity(_GLK.getListCitiesUkraina(Arrays.asList(EnumCitiesUKRAIN.values()),"ВСЕ ГОРОДА"));
                        break;
                    case "Узбекистан":
                        //
                        completeSpinnerCity(_GLK.getListCitiesUzbekistan(Arrays.asList(EnumCitiesUZBEKISTAN.values()),"ВСЕ ГОРОДА"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void completeSpinnerCity(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner_city.setAdapter(adapter);
    }
    private void completeSpinnerCategory() {
        _GLK = new GENERATE_LISTS_CLASS();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                , android.R.layout.simple_spinner_item, _GLK.getListCategories(Arrays.asList(EnumForCategories.values()), "ВСЕ КАТЕГОРИИ"));
        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("АВТО")) {
                    car_container.setVisibility(View.VISIBLE);
                    setTextsAuto();
                } else if (parent.getItemAtPosition(position).toString().equals("НЕДВИЖИМОСТЬ")) {
                    car_container.setVisibility(View.VISIBLE);
                    setTexts();
                } else {
                    car_container.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setTextsAuto() {
        consistance_auto.setHint("Состояние авто");
        marka_auto.setHint("Марка авто");
        model_auto.setHint("Модель авто");
        type_of_body.setHint("Тип кузова");
        colot.setHint("Цвет");
        region.setHint("Регион");
        category.setHint("Категория");
        year_production_car.setHint("Год выпуска");
        how_much_completed_road.setHint("Пробег");
        type_of_engine.setHint("Тип двигателя");
        value_engine.setHint("Объем");
        transmission.setHint("Тип трансмиссии");
    }
    private void setTexts() {
        consistance_auto.setHint("Количство комнат");
        marka_auto.setHint("Общая площадь");
        model_auto.setHint("Площадь кухни");
        type_of_body.setHint("Этаж и этажность дома");
        colot.setHint("Балкон");
        region.setHint("Санузлы");
        category.setHint("Ремонт");
        year_production_car.setHint("Имеется ли лифт");
        how_much_completed_road.setHint("Тип дома");
        type_of_engine.setHint("Инфраструктура");
        value_engine.setHint("Год постройки");
        transmission.setHint("Вода/гад/канализация");
    }
    private ArrayList<String> generateListForSpnner() {
        ArrayList<String> list = new ArrayList<>();
        list.add("$");
        list.add("\u20BD ");
        list.add("€");
        return list;
    }
    public void onClickAd(View view) {
        switch (view.getId()) {
            case R.id.imageAd_1:
                // нажатие на изображение
                //getPhoto();
                // нужно выбрать откуда брать фото
                RETURNED_PHOTO = 1;
                new AlertDialog.Builder(AddAd.this)
                        .setTitle("Выбрать фото")
                        .setPositiveButton("Камера", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //the user wants to leave - so dismiss the dialog and exit
                                dispatchTakePictureIntentTwo();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Галерея", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        takePhotoFromGallery();
                        dialog.dismiss();
                    }
                }).show();
                //dispatchTakePictureIntentTwo();
                break;
            case R.id.imageAd_2:
                RETURNED_PHOTO = 2;
                // нажатие на изображение
                //getPhoto();
                // нужно выбрать откуда брать фото
                new AlertDialog.Builder(AddAd.this)
                        .setTitle("Выбрать фото")
                        .setPositiveButton("Камера", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //the user wants to leave - so dismiss the dialog and exit
                                dispatchTakePictureIntentTwo();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Галерея", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        takePhotoFromGallery();
                        dialog.dismiss();
                    }
                }).show();
                //dispatchTakePictureIntentTwo();
                break;
            case R.id.imageAd_3:
                RETURNED_PHOTO = 3;
                // нажатие на изображение
                //getPhoto();
                // нужно выбрать откуда брать фото
                new AlertDialog.Builder(AddAd.this)
                        .setTitle("Выбрать фото")
                        .setPositiveButton("Камера", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //the user wants to leave - so dismiss the dialog and exit
                                dispatchTakePictureIntentTwo();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Галерея", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        takePhotoFromGallery();
                        dialog.dismiss();
                    }
                }).show();
                //dispatchTakePictureIntentTwo();
                break;
            case R.id.imageAd_4:
                RETURNED_PHOTO = 4;
                // нажатие на изображение
                //getPhoto();
                // нужно выбрать откуда брать фото
                new AlertDialog.Builder(AddAd.this)
                        .setTitle("Выбрать фото")
                        .setPositiveButton("Камера", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //the user wants to leave - so dismiss the dialog and exit
                                dispatchTakePictureIntentTwo();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Галерея", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        takePhotoFromGallery();
                        dialog.dismiss();
                    }
                }).show();
                //dispatchTakePictureIntentTwo();
                break;
            case R.id.imageAd_5:
                RETURNED_PHOTO = 5;
                // нажатие на изображение
                //getPhoto();
                // нужно выбрать откуда брать фото
                new AlertDialog.Builder(AddAd.this)
                        .setTitle("Выбрать фото")
                        .setPositiveButton("Камера", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //the user wants to leave - so dismiss the dialog and exit
                                dispatchTakePictureIntentTwo();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Галерея", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        takePhotoFromGallery();
                        dialog.dismiss();
                    }
                }).show();
                //dispatchTakePictureIntentTwo();
                break;
            case R.id.cancel_button_ad:
                new AlertDialog.Builder(AddAd.this)
                        .setTitle("Выход")
                        .setMessage("Вы уверены что хотите выйти не сохраняя данные?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //the user wants to leave - so dismiss the dialog and exit
                                finish();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.add_ad_button:
                //добавляем объявление
                //Log.d(MainActivity.LOG_TAG,"drawable = "+image_ad);
                if (!edit_text_name_ad.getText().toString().equals("") &&
                        !edit_text_cost_job.getText().toString().equals("") &&
                        !edit_text_contacts_ad.getText().toString().equals("") &&
                        !edit_text_about_job_ad.getText().toString().equals("") &&
                        photo_1 != null || photo_2 != null || photo_3 != null || photo_4 != null || photo_5 != null) {
                    progress_bar_ad.setVisibility(View.VISIBLE);
                    container_ad_frame_layout.setAlpha(0.3f);
                    button_add_ad.setClickable(false);
                    button_cancel_ad.setClickable(false);
                    add_ad(edit_text_name_ad.getText().toString()
                            , edit_text_name_ad.getText().toString()
                            , Integer.parseInt(edit_text_cost_job.getText().toString())
                            , edit_text_contacts_ad.getText().toString()
                            , edit_text_about_job_ad.getText().toString()
                            , spinner_type_money.getSelectedItem().toString()
                            , spinner_city.getSelectedItem().toString());
                } else if (!edit_text_name_ad.getText().toString().equals("") &&
                        !edit_text_name_ad.getText().toString().equals("") &&
                        !edit_text_cost_job.getText().toString().equals("") &&
                        !edit_text_contacts_ad.getText().toString().equals("") &&
                        !edit_text_about_job_ad.getText().toString().equals("") &&
                        photo_1 == null && photo_2 == null && photo_3 == null && photo_4 == null && photo_5 == null) {
                    add_ad_withoutPhoto(edit_text_name_ad.getText().toString()
                            , edit_text_name_ad.getText().toString()
                            , Integer.parseInt(edit_text_cost_job.getText().toString())
                            , edit_text_contacts_ad.getText().toString()
                            , edit_text_about_job_ad.getText().toString()
                            , spinner_type_money.getSelectedItem().toString(),
                            "https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb"
                            , spinner_city.getSelectedItem().toString());
                } else {
                    Snackbar.make(view, "Заполните все поля и сделайте фото", BaseTransientBottomBar.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
    private void takePhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_AD);
    }
    private ArrayAdapter<String> generateAdapter(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        return adapter;
    }
    private void add_ad(String name_people, String name_job, int cost_job, String contacts, String about_job, String type_money, String city) {

        String about = "";
        if (!edit_text_about_job_ad.getText().toString().equals("")){
            about = edit_text_about_job_ad.getText().toString();
        }


        if (!spinner_city.getSelectedItem().toString().equals("ВСЕ ГОРОДА")){
            city = spinner_country.getSelectedItem().toString() + ", "
                    + spinner_city.getSelectedItem().toString();
        }


        urlPathPhoto_1 = "";
        urlPathPhoto_2 = "";
        urlPathPhoto_3 = "";
        urlPathPhoto_4 = "";
        urlPathPhoto_5 = "";
        if (spinner_category.getSelectedItem().toString().equals("АВТО")) {
            about = "Состояние авто: " + consistance_auto.getText().toString() + "\n" +
                    "Марка авто: " + marka_auto.getText().toString() + "\n" +
                    "Модель авто: " + model_auto.getText().toString() + "\n" +
                    "Тип кузова: " + type_of_body.getText().toString() + "\n" +
                    "Цвет: " + colot.getText().toString() + "\n" +
                    "Регион: " + region.getText().toString() + "\n" +
                    "Категория: " + category.getText().toString() + "\n" +
                    "Год выпуска: " + year_production_car.getText().toString() + "\n" +
                    "Пробег: " + how_much_completed_road.getText().toString() + "\n" +
                    "Тип двигателя: " + type_of_engine.getText().toString() + "\n" +
                    "Объем: " + value_engine.getText().toString() + "\n" +
                    "Тип трансмиссии: " + transmission.getText().toString() + "\n" +
                    "Цвет:" +color.getText().toString();
        } else if (spinner_category.getSelectedItem().toString().equals("НЕДВИЖИМОСТЬ")) {
            about = "Количство комнат: " + consistance_auto.getText().toString() + "\n" +
                    "Общая площадь: " + marka_auto.getText().toString() + "\n" +
                    "Площадь кухни: " + model_auto.getText().toString() + "\n" +
                    "Этаж и этажность дома: " + type_of_body.getText().toString() + "\n" +
                    "Балкон: " + colot.getText().toString() + "\n" +
                    "Санузлы: " + region.getText().toString() + "\n" +
                    "Ремонт: " + category.getText().toString() + "\n" +
                    "Имеется ли лифт: " + year_production_car.getText().toString() + "\n" +
                    "Тип дома: " + how_much_completed_road.getText().toString() + "\n" +
                    "Инфраструктура: " + type_of_engine.getText().toString() + "\n" +
                    "Год постройки: " + value_engine.getText().toString() + "\n" +
                    "Вода/газ/канализация: " + transmission.getText().toString()+
                    "Цвет:" +color.getText().toString();
        } else if (!spinner_category.getSelectedItem().toString().equals("НЕДВИЖИМОСТЬ") || !spinner_category.getSelectedItem().toString().equals("АВТО")){
            if (adFromEditDetail != null) {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, urlPathPhoto_1, urlPathPhoto_2, urlPathPhoto_3, urlPathPhoto_4, urlPathPhoto_5, cost_job, contacts, adFromEditDetail.getDateAd(), type_money, user, 0, "", false, city);
            } else {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, cost_job, contacts, new Date().getTime(), type_money, user, 0, "", false, city);
            }
        }
        uploadPhoto(photo_1);
        uploadPhoto(photo_2);
        uploadPhoto(photo_3);
        uploadPhoto(photo_4);
        uploadPhoto(photo_5);
        ad.setCity(spinner_city.getSelectedItem().toString());
    }

    private void add_ad_withoutPhoto(String name_people, String name_job, int cost_job, String contacts, String about_job, String type_money, String url, String city) {
        urlPathPhoto_1 = "";
        urlPathPhoto_2 = "";
        urlPathPhoto_3 = "";
        urlPathPhoto_4 = "";
        urlPathPhoto_5 = "";
        if (spinner_category.getSelectedItem().toString().equals("АВТО")) {
            String about = "Состояние авто: " + consistance_auto.getText().toString() + "\n" +
                    "Марка авто: " + marka_auto.getText().toString() + "\n" +
                    "Модель авто: " + model_auto.getText().toString() + "\n" +
                    "Тип кузова: " + type_of_body.getText().toString() + "\n" +
                    "Цвет: " + colot.getText().toString() + "\n" +
                    "Регион: " + region.getText().toString() + "\n" +
                    "Категория: " + category.getText().toString() + "\n" +
                    "Год выпуска: " + year_production_car.getText().toString() + "\n" +
                    "Пробег: " + how_much_completed_road.getText().toString() + "\n" +
                    "Тип двигателя: " + type_of_engine.getText().toString() + "\n" +
                    "Объем: " + value_engine.getText().toString() + "\n" +
                    "Тип трансмиссии: " + transmission.getText().toString()+
                    "Цвет:" +color.getText().toString();
            if (adFromEditDetail != null) {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, urlPathPhoto_1, urlPathPhoto_2, urlPathPhoto_3, urlPathPhoto_4, urlPathPhoto_5, cost_job, contacts, adFromEditDetail.getDateAd(), type_money, user, 0, "", false, city);
            } else {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, cost_job, contacts, new Date().getTime(), type_money, user, 0, "", false, city);
            }
        } else if (spinner_category.getSelectedItem().toString().equals("НЕДВИЖИМОСТЬ")) {
            String about = "Количство комнат: " + consistance_auto.getText().toString() + "\n" +
                    "Общая площадь: " + marka_auto.getText().toString() + "\n" +
                    "Площадь кухни: " + model_auto.getText().toString() + "\n" +
                    "Этаж и этажность дома: " + type_of_body.getText().toString() + "\n" +
                    "Балкон: " + colot.getText().toString() + "\n" +
                    "Санузлы: " + region.getText().toString() + "\n" +
                    "Ремонт: " + category.getText().toString() + "\n" +
                    "Имеется ли лифт: " + year_production_car.getText().toString() + "\n" +
                    "Тип дома: " + how_much_completed_road.getText().toString() + "\n" +
                    "Инфраструктура: " + type_of_engine.getText().toString() + "\n" +
                    "Год постройки: " + value_engine.getText().toString() + "\n" +
                    "Вода/газ/канализация: " + transmission.getText().toString() +
                    "Цвет:" +color.getText().toString();
            if (adFromEditDetail != null) {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, urlPathPhoto_1, urlPathPhoto_2, urlPathPhoto_3, urlPathPhoto_4, urlPathPhoto_5, cost_job, contacts, adFromEditDetail.getDateAd(), type_money, user, 0, "", false, city);
            } else {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, cost_job, contacts, new Date().getTime(), type_money, user, 0, "", false, city);
            }
        } else if (!spinner_category.getSelectedItem().toString().equals("НЕДВИЖИМОСТЬ") || !spinner_category.getSelectedItem().toString().equals("АВТО")){
            if (adFromEditDetail != null) {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about_job, urlPathPhoto_1, urlPathPhoto_2, urlPathPhoto_3, urlPathPhoto_4, urlPathPhoto_5, cost_job, contacts, adFromEditDetail.getDateAd(), type_money, user, 0, "", false, city);
            } else {
                ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about_job, cost_job, contacts, new Date().getTime(), type_money, user, 0, "", false, city);
            }
        }
        String about = "";
        if (!edit_text_about_job_ad.getText().toString().equals("")){
             about = edit_text_about_job_ad.getText().toString();
        }

        if (adFromEditDetail != null) {

            ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, urlPathPhoto_1, urlPathPhoto_2, urlPathPhoto_3, urlPathPhoto_4, urlPathPhoto_5, cost_job, contacts, adFromEditDetail.getDateAd(), type_money, user, 0, "", false, city);
        } else {
            ad = new Ad(false, spinner_category.getSelectedItem().toString(), name_people, name_job, about, cost_job, contacts, new Date().getTime(), type_money, user, 0, "", false, city);
        }
        ad.setImagePathAd_1(url);
        ad.setImagePathAd_2(url);
        ad.setImagePathAd_3(url);
        ad.setImagePathAd_4(url);
        ad.setImagePathAd_5(url);
        reference.child("ads").child(ad.getDateAd() + "").setValue(ad);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri filePath = null;
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();
                try {
                    if (RETURNED_PHOTO == 1) {
                        photo_1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_1); // тут мы получаем полноценное изображение
                        image_ad_1.setImageBitmap(photo_1);
                    }
                    if (RETURNED_PHOTO == 2) {
                        photo_2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_2); // тут мы получаем полноценное изображение
                        image_ad_2.setImageBitmap(photo_2);
                    }
                    if (RETURNED_PHOTO == 3) {
                        photo_3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_3); // тут мы получаем полноценное изображение
                        image_ad_3.setImageBitmap(photo_3);
                    }
                    if (RETURNED_PHOTO == 4) {
                        photo_4 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_4); // тут мы получаем полноценное изображение
                        image_ad_4.setImageBitmap(photo_4);
                    }
                    if (RETURNED_PHOTO == 5) {
                        photo_5 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_5); // тут мы получаем полноценное изображение
                        image_ad_5.setImageBitmap(photo_5);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (resultCode == RESULT_CANCELED) {
                }
            }
        } else if (requestCode == PICK_IMAGE_REQUEST_AD) {
            filePath = data.getData();
            try {
                if (RETURNED_PHOTO == 1) {
                    photo_1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_1.setImageBitmap(photo_1);
                }
                if (RETURNED_PHOTO == 2) {
                    photo_2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_2.setImageBitmap(photo_2);
                }
                if (RETURNED_PHOTO == 3) {
                    photo_3 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_3.setImageBitmap(photo_3);
                }
                if (RETURNED_PHOTO == 4) {
                    photo_4 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_4.setImageBitmap(photo_4);
                }
                if (RETURNED_PHOTO == 5) {
                    photo_5 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_5.setImageBitmap(photo_5);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadPhoto(Bitmap bitmap) {
        iterator++;
        String namePhoto = user.getNickName() + "_" + ad.getNameAd() + "_" + ad.getNameJobAd() + new Date().getTime() + ".jpg"; // уникальное имя фото
        StorageReference mountainsRef = storageRef.child(namePhoto);

        //Log.d(MainActivity.LOG_TAG,"iterator = " + iterator);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    iterator_sucsess++;
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    downloadUrl = null;
                    downloadUrl = taskSnapshot.getDownloadUrl();
                    Uri u = taskSnapshot.getDownloadUrl();

                    if (iterator_sucsess == 1) {
                        ad.setImagePathAd_1(u + "");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd() + "").child("imagePathAd_1")
                                .setValue(ad.getImagePathAd_1());
                    }
                    if (iterator_sucsess == 2) {
                        ad.setImagePathAd_2(u + "");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd() + "").child("imagePathAd_2")
                                .setValue(ad.getImagePathAd_2());
                    }
                    if (iterator_sucsess == 3) {
                        ad.setImagePathAd_3(u + "");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd() + "").child("imagePathAd_3")
                                .setValue(ad.getImagePathAd_3());
                    }
                    if (iterator_sucsess == 4) {
                        ad.setImagePathAd_4(u + "");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd() + "").child("imagePathAd_4")
                                .setValue(ad.getImagePathAd_4());
                    }
                    if (iterator_sucsess == 5) {
                        ad.setImagePathAd_5(u + "");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd() + "").child("imagePathAd_5")
                                .setValue(ad.getImagePathAd_5());
                    }
                }
            });
        } else {
            if (iterator == 1) {
                ad.setImagePathAd_1("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator == 2) {
                ad.setImagePathAd_2("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator == 3) {
                ad.setImagePathAd_3("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator == 4) {
                ad.setImagePathAd_4("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator == 5) {
                ad.setImagePathAd_5("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
        }
        if (iterator == 5) {
            reference.child("ads").child(ad.getDateAd() + "").setValue(ad);
            finish();
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntentTwo() {
        //Log.d(MainActivity.LOG_TAG,"1");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                if (RETURNED_PHOTO == 1) {
                    photoURI_1 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_1);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO == 2) {
                    photoURI_2 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_2);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO == 3) {
                    photoURI_3 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_3);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO == 4) {
                    photoURI_4 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_4);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO == 5) {
                    photoURI_5 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_5);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
    }
}
