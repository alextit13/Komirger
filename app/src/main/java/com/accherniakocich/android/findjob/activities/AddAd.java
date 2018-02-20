package com.accherniakocich.android.findjob.activities;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.User;
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
import java.util.Date;

public class AddAd extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 500;

    public static final int PICK_IMAGE_REQUEST_AD = 72;

    private User user;
    private static final int CAMERA_REQUEST = 1888;
    private StorageReference storageRef;
    private ImageView image_ad_1,image_ad_2,image_ad_3,image_ad_4,image_ad_5;
    private EditText edit_text_name_ad,edit_text_name_job_ad,edit_text_cost_job,edit_text_contacts_ad,edit_text_about_job_ad,city;
    private Spinner spinner_type_money;
    private Button button_cancel_ad,button_add_ad;
    private ProgressBar progress_bar_ad;
    private FrameLayout container_ad_frame_layout;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Bitmap photo_1;
    private Bitmap photo_2;
    private Bitmap photo_3;
    private Bitmap photo_4;
    private Bitmap photo_5;
    private Ad ad;
    private Uri photoURI_1;
    private Uri photoURI_2;
    private Uri photoURI_3;
    private Uri photoURI_4;
    private Uri photoURI_5;
    private String mCurrentPhotoPath;
    private String urlPathPhoto_1;
    private String urlPathPhoto_2;
    private String urlPathPhoto_3;
    private String urlPathPhoto_4;
    private String urlPathPhoto_5;
    private Ad adFromEditDetail;
    private int RETURNED_PHOTO = 0;
    private Spinner spinner_category,spinner_city;
    private int iterator = 0;
    private Uri downloadUrl;
    int iterator_sucsess = 0;
    private ArrayList<String>cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        init();
    }

    private void init() {
        image_ad_1 = (ImageView)findViewById(R.id.imageAd_1);
        image_ad_2 = (ImageView)findViewById(R.id.imageAd_2);
        image_ad_3 = (ImageView)findViewById(R.id.imageAd_3);
        image_ad_4 = (ImageView)findViewById(R.id.imageAd_4);
        image_ad_5 = (ImageView)findViewById(R.id.imageAd_5);


        spinner_category = (Spinner)findViewById(R.id.spinner_category);
        completeSpinnerCategory();

        spinner_city = (Spinner)findViewById(R.id.spinner_city);
        completeSpinnerCity();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        storageRef = FirebaseStorage.getInstance().getReference();


        city = (EditText)findViewById(R.id.city);
        edit_text_name_ad = (EditText)findViewById(R.id.name_ad);
        edit_text_name_job_ad = (EditText)findViewById(R.id.name_job_ad);
        edit_text_cost_job = (EditText)findViewById(R.id.cost_job);
        edit_text_contacts_ad = (EditText)findViewById(R.id.contacts_ad);
        edit_text_about_job_ad = (EditText)findViewById(R.id.about_job);
        //image_ad = (ImageView)findViewById(R.id.imageAd);
        spinner_type_money = (Spinner)findViewById(R.id.spinner_add_ad);
        spinner_type_money.setAdapter(generateAdapter(generateListForSpnner()));
        button_cancel_ad = (Button)findViewById(R.id.cancel_button_ad);
        button_add_ad = (Button)findViewById(R.id.add_ad_button);
        container_ad_frame_layout = (FrameLayout)findViewById(R.id.container_ad_frame_layout);
        progress_bar_ad = (ProgressBar)findViewById(R.id.progress_bar_ad);
        progress_bar_ad.setVisibility(View.INVISIBLE);

        //Log.d(MainActivity.LOG_TAG,"photo = "+photo.toString());
        int request = 0;
        try {
           request = intent.getIntExtra("request",0);
        }catch (Exception e){
            Log.d(MainActivity.LOG_TAG,"Exception");
        }
        if (request!=0){
            adFromEditDetail = (Ad) intent.getSerializableExtra("ad");
            User userFromEditDetail = (User) intent.getSerializableExtra("user");

            edit_text_name_ad.setText(userFromEditDetail.getNickName());
            edit_text_name_job_ad.setText(adFromEditDetail.getNameJobAd());
            edit_text_cost_job.setText(adFromEditDetail.getCostAd()+"");
            edit_text_about_job_ad.setText(adFromEditDetail.getTextAd());
            edit_text_contacts_ad.setText(adFromEditDetail.getPeopleSourceAd()+"");
        }
    }

    private void completeSpinnerCity() {
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,completeArrayList());
        spinner_city.setAdapter(adapter);
    }

    private void completeSpinnerCategory() {
        ArrayList<String>categorys = new ArrayList<>(20);
        categorys.add("Работа");
        categorys.add("Недвижимость");
        categorys.add("Женский гардероб");
        categorys.add("Мужской гардероб");
        categorys.add("Детски гардероб");
        categorys.add("Детские товары");
        categorys.add("Хендмейд");
        categorys.add("Авто и мото");
        categorys.add("Телефоны и планшеты");
        categorys.add("Фото и видеокамеры");
        categorys.add("Компьютеры");
        categorys.add("Электроника и бытовая техника");
        categorys.add("Для дома для дачи");
        categorys.add("Ремонт и строительство");
        categorys.add("Красота и здоровье");
        categorys.add("Спорт и отдых");
        categorys.add("Хобби и развлечения");
        categorys.add("Животные");
        categorys.add("Для бизнеса");
        categorys.add("Прочее");
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getApplicationContext()
        ,android.R.layout.simple_spinner_item,categorys);
        spinner_category.setAdapter(adapter);
    }

    private ArrayList<String> generateListForSpnner(){
        ArrayList<String>list = new ArrayList<>();
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
                        !edit_text_name_job_ad.getText().toString().equals("") &&
                        !edit_text_cost_job.getText().toString().equals("") &&
                        !edit_text_contacts_ad.getText().toString().equals("") &&
                        !edit_text_about_job_ad.getText().toString().equals("") &&
                        photo_1!=null||photo_2!=null||photo_3!=null||photo_4!=null||photo_5!=null) {
                    progress_bar_ad.setVisibility(View.VISIBLE);
                    container_ad_frame_layout.setAlpha(0.3f);
                    button_add_ad.setClickable(false);
                    button_cancel_ad.setClickable(false);
                    add_ad(edit_text_name_ad.getText().toString()
                            , edit_text_name_job_ad.getText().toString()
                            , Integer.parseInt(edit_text_cost_job.getText().toString())
                            , edit_text_contacts_ad.getText().toString()
                            , edit_text_about_job_ad.getText().toString()
                            , spinner_type_money.getSelectedItem().toString()
                    ,spinner_city.getSelectedItem().toString());
                }else if(!edit_text_name_ad.getText().toString().equals("") &&
                        !edit_text_name_job_ad.getText().toString().equals("") &&
                        !edit_text_cost_job.getText().toString().equals("") &&
                        !edit_text_contacts_ad.getText().toString().equals("") &&
                        !edit_text_about_job_ad.getText().toString().equals("") &&
                        photo_1==null&&photo_2==null&&photo_3==null&&photo_4==null&&photo_5==null){
                    add_ad_withoutPhoto(edit_text_name_ad.getText().toString()
                            , edit_text_name_job_ad.getText().toString()
                            , Integer.parseInt(edit_text_cost_job.getText().toString())
                            , edit_text_contacts_ad.getText().toString()
                            , edit_text_about_job_ad.getText().toString()
                            , spinner_type_money.getSelectedItem().toString(),
                            "https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb"
                            ,spinner_city.getSelectedItem().toString());
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

    private ArrayAdapter<String> generateAdapter(ArrayList<String> list){
        ArrayAdapter <String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        return adapter;
    }

    private void add_ad(String name_people, String name_job, int cost_job,
                        String contacts, String about_job, String type_money, String city) {
        urlPathPhoto_1 = "";
        urlPathPhoto_2 = "";
        urlPathPhoto_3 = "";
        urlPathPhoto_4 = "";
        urlPathPhoto_5 = "";

        Log.d(MainActivity.LOG_TAG,"adFromEditDetail = " + adFromEditDetail);
        if (adFromEditDetail!=null){
            ad = new Ad(false,spinner_category.getSelectedItem().toString(),name_people,name_job,about_job,urlPathPhoto_1,urlPathPhoto_2,urlPathPhoto_3,urlPathPhoto_4,urlPathPhoto_5,cost_job,contacts,adFromEditDetail.getDateAd(),type_money,user,0,"",false,city);
        }else{
            ad = new Ad(false, spinner_category.getSelectedItem().toString(),name_people,name_job,about_job,cost_job,contacts,new Date().getTime(),type_money,user,0,"",false,city);
        }

        uploadPhoto(photo_1);
        uploadPhoto(photo_2);
        uploadPhoto(photo_3);
        uploadPhoto(photo_4);
        uploadPhoto(photo_5);

        ad.setCity(spinner_city.getSelectedItem().toString());

    }

    private void add_ad_withoutPhoto(String name_people, String name_job, int cost_job,
                        String contacts, String about_job, String type_money,String url
    ,String city) {
        urlPathPhoto_1 = "";
        urlPathPhoto_2 = "";
        urlPathPhoto_3 = "";
        urlPathPhoto_4 = "";
        urlPathPhoto_5 = "";
        //Log.d(MainActivity.LOG_TAG,"adFromEditDetail = " + adFromEditDetail);

        if (adFromEditDetail!=null){
            ad = new Ad(false, spinner_category.getSelectedItem().toString(),name_people,name_job,about_job, urlPathPhoto_1,urlPathPhoto_2,urlPathPhoto_3,urlPathPhoto_4,urlPathPhoto_5, cost_job,contacts,adFromEditDetail.getDateAd(),type_money,user,0,"",false,city);
        }else{
            ad = new Ad(false, spinner_category.getSelectedItem().toString(),name_people,name_job,about_job, cost_job,contacts,new Date().getTime(),type_money,user,0,"",false,city);
        }

        ad.setImagePathAd_1(url);
        ad.setImagePathAd_2(url);
        ad.setImagePathAd_3(url);
        ad.setImagePathAd_4(url);
        ad.setImagePathAd_5(url);
        //reference.child("ads").child(nameChild+"").setValue(ad);
        reference.child("ads").child(ad.getDateAd()+"").setValue(ad);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri filePath = null;
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved", Toast.LENGTH_LONG).show();

                try {
                    if (RETURNED_PHOTO==1){
                        photo_1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_1); // тут мы получаем полноценное изображение
                        image_ad_1.setImageBitmap(photo_1);
                    }
                    if (RETURNED_PHOTO==2){
                        photo_2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_2); // тут мы получаем полноценное изображение
                        image_ad_2.setImageBitmap(photo_2);
                    }
                    if (RETURNED_PHOTO==3){
                        photo_3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_3); // тут мы получаем полноценное изображение
                        image_ad_3.setImageBitmap(photo_3);
                    }
                    if (RETURNED_PHOTO==4){
                        photo_4 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_4); // тут мы получаем полноценное изображение
                        image_ad_4.setImageBitmap(photo_4);
                    }
                    if (RETURNED_PHOTO==5){
                        photo_5 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI_5); // тут мы получаем полноценное изображение
                        image_ad_5.setImageBitmap(photo_5);
                    }
                    //Log.d(MainActivity.LOG_TAG,"photo image = "+bitmap.getWidth());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                    // So need to Delete the path from DB
                }
            }
        }else if (requestCode == PICK_IMAGE_REQUEST_AD){
            filePath = data.getData();
            try {
                if (RETURNED_PHOTO==1){
                    photo_1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_1.setImageBitmap(photo_1);
                }
                if (RETURNED_PHOTO==2){
                    photo_2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_2.setImageBitmap(photo_2);
                }
                if (RETURNED_PHOTO==3){
                    photo_3 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_3.setImageBitmap(photo_3);
                }
                if (RETURNED_PHOTO==4){
                    photo_4 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_4.setImageBitmap(photo_4);
                }
                if (RETURNED_PHOTO==5){
                    photo_5 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image_ad_5.setImageBitmap(photo_5);
                }

                //uploadPhoto(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    private void uploadPhoto(Bitmap bitmap) {
        iterator++;
        String namePhoto = user.getNickName()+"_"+ad.getNameAd()+"_"+ad.getNameJobAd()+new Date().getTime()+".jpg"; // уникальное имя фото
        StorageReference mountainsRef = storageRef.child(namePhoto);

        //Log.d(MainActivity.LOG_TAG,"iterator = " + iterator);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bitmap!=null){
            Log.d(MainActivity.LOG_TAG,"bitmap!=null");
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
                    downloadUrl=null;
                    downloadUrl = taskSnapshot.getDownloadUrl();
                    Uri u = taskSnapshot.getDownloadUrl();
                    Log.d(MainActivity.LOG_TAG,"bitmap = " + u);

                    if (iterator_sucsess==1){
                        ad.setImagePathAd_1(u+"");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"").child("imagePathAd_1")
                                .setValue(ad.getImagePathAd_1());
                    }
                    if (iterator_sucsess==2){
                        ad.setImagePathAd_2(u+"");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"").child("imagePathAd_2")
                                .setValue(ad.getImagePathAd_2());
                    }
                    if (iterator_sucsess==3){
                        ad.setImagePathAd_3(u+"");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"").child("imagePathAd_3")
                                .setValue(ad.getImagePathAd_3());
                    }
                    if (iterator_sucsess==4){
                        ad.setImagePathAd_4(u+"");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"").child("imagePathAd_4")
                                .setValue(ad.getImagePathAd_4());
                    }
                    if (iterator_sucsess==5){
                        ad.setImagePathAd_5(u+"");
                        FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"").child("imagePathAd_5")
                                .setValue(ad.getImagePathAd_5());
                    }

                    //reference.child("ads").child(nameChild+"").setValue(ad);
                    //reference.child("ads").child(ad.getDateAd()+"").setValue(ad);
                    //Log.d(MainActivity.LOG_TAG,urlPathPhoto);
                    //Toast.makeText(AddAd.this, "Объявление подано!", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            });
        }else{
            Log.d(MainActivity.LOG_TAG,"bitmap==null");
            if (iterator==1){
                ad.setImagePathAd_1("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator==2){
                ad.setImagePathAd_2("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator==3){
                ad.setImagePathAd_3("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator==4){
                ad.setImagePathAd_4("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
            if (iterator==5){
                ad.setImagePathAd_5("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb");
            }
        }
        if (iterator==5){
            reference.child("ads").child(ad.getDateAd()+"").setValue(ad);
            finish();
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        Log.d(MainActivity.LOG_TAG,"go");
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
                if (RETURNED_PHOTO==1){
                    photoURI_1 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_1);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO==2){
                    photoURI_2 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_2);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO==3){
                    photoURI_3 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_3);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO==4){
                    photoURI_4 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_4);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
                if (RETURNED_PHOTO==5){
                    photoURI_5 = FileProvider.getUriForFile(this, "com.accherniakocich.android.findjob", photoFile);
                    /*Log.d(MainActivity.LOG_TAG,"photoFile = "+photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI_5);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        }
    }







    private ArrayList<String> completeArrayList() {
        cities = new ArrayList<>();
        cities.add("Абакан");
        cities.add("Азов");
        cities.add("Александров");
        cities.add("Алексин");
        cities.add("Альметьевск");
        cities.add("Анапа");
        cities.add("Ангарск");
        cities.add("Анжеро-Судженск");
        cities.add("Апатиты");
        cities.add("Арзамас");
        cities.add("Армавир");
        cities.add("Арсеньев");
        cities.add("Артем");
        cities.add("Архангельск");
        cities.add("Асбест");
        cities.add("Астрахань");
        cities.add("Ачинск");
        cities.add("Балаково");
        cities.add("Балахна");
        cities.add("Балашиха");
        cities.add("Балашов");
        cities.add("Барнаул");
        cities.add("Батайск");
        cities.add("Белгород");
        cities.add("Белебей");
        cities.add("Белово");
        cities.add("Белогорск (Амурская область)");
        cities.add("Белорецк");
        cities.add("Белореченск");
        cities.add("Бердск");
        cities.add("Березники");
        cities.add("Березовский (Свердловская область)");
        cities.add("Бийск");
        cities.add("Биробиджан");
        cities.add("Благовещенск (Амурская область)");
        cities.add("Бор");
        cities.add("Борисоглебск");
        cities.add("Боровичи");
        cities.add("Братск");
        cities.add("Брянск");
        cities.add("Бугульма");
        cities.add("Буденновск");
        cities.add("Бузулук");
        cities.add("Буйнакск");
        cities.add("Великие Луки");
        cities.add("Великий Новгород");
        cities.add("Верхняя Пышма");
        cities.add("Видное");
        cities.add("Владивосток");
        cities.add("Владикавказ");
        cities.add("Владимир");
        cities.add("Волгоград");
        cities.add("Волгодонск");
        cities.add("Волжск");
        cities.add("Волжский");
        cities.add("Вологда");
        cities.add("Вольск");
        cities.add("Воркута");
        cities.add("Воронеж");
        cities.add("Воскресенск");
        cities.add("Воткинск");
        cities.add("Всеволожск");
        cities.add("Выборг");
        cities.add("Выкса");
        cities.add("Вязьма");
        cities.add("Гатчина");
        cities.add("Геленджик");
        cities.add("Георгиевск");
        cities.add("Глазов");
        cities.add("Горно-Алтайск");
        cities.add("Грозный");
        cities.add("Губкин");
        cities.add("Гудермес");
        cities.add("Гуково");
        cities.add("Гусь-Хрустальный");
        cities.add("Дербент");
        cities.add("Дзержинск");
        cities.add("Димитровград");
        cities.add("Дмитров");
        cities.add("Долгопрудный");
        cities.add("Домодедово");
        cities.add("Донской");
        cities.add("Дубна");
        cities.add("Евпатория");
        cities.add("Егорьевск");
        cities.add("Ейск");
        cities.add("Екатеринбург");
        cities.add("Елабуга");
        cities.add("Елец");
        cities.add("Ессентуки");
        cities.add("Железногорск (Красноярский край)");
        cities.add("Железногорск (Курская область)");
        cities.add("Жигулевск");
        cities.add("Жуковский");
        cities.add("Заречный");
        cities.add("Зеленогорск");
        cities.add("Зеленодольск");
        cities.add("Златоуст");
        cities.add("Иваново");
        cities.add("Ивантеевка");
        cities.add("Ижевск");
        cities.add("Избербаш");
        cities.add("Иркутск");
        cities.add("Искитим");
        cities.add("Ишим");
        cities.add("Ишимбай");
        cities.add("Йошкар-Ола");
        cities.add("Казань");
        cities.add("Калининград");
        cities.add("Калуга");
        cities.add("Каменск-Уральский");
        cities.add("Каменск-Шахтинский");
        cities.add("Камышин");
        cities.add("Канск");
        cities.add("Каспийск");
        cities.add("Кемерово");
        cities.add("Керчь");
        cities.add("Кинешма");
        cities.add("Кириши");
        cities.add("Киров (Кировская область)");
        cities.add("Кирово-Чепецк");
        cities.add("Киселевск");
        cities.add("Кисловодск");
        cities.add("Клин");
        cities.add("Клинцы");
        cities.add("Ковров");
        cities.add("Когалым");
        cities.add("Коломна");
        cities.add("Комсомольск-на-Амуре");
        cities.add("Копейск");
        cities.add("Королев");
        cities.add("Кострома");
        cities.add("Котлас");
        cities.add("Красногорск");
        cities.add("Краснодар");
        cities.add("Краснокаменск");
        cities.add("Краснокамск");
        cities.add("Краснотурьинск");
        cities.add("Красноярск");
        cities.add("Кропоткин");
        cities.add("Крымск");
        cities.add("Кстово");
        cities.add("Кузнецк");
        cities.add("Кумертау");
        cities.add("Кунгур");
        cities.add("Курган");
        cities.add("Курск");
        cities.add("Кызыл");
        cities.add("Лабинск");
        cities.add("Лениногорск");
        cities.add("Ленинск-Кузнецкий");
        cities.add("Лесосибирск");
        cities.add("Липецк");
        cities.add("Лиски");
        cities.add("Лобня");
        cities.add("Лысьва");
        cities.add("Лыткарино");
        cities.add("Люберцы");
        cities.add("Магадан");
        cities.add("Магнитогорск");
        cities.add("Майкоп");
        cities.add("Махачкала");
        cities.add("Междуреченск");
        cities.add("Мелеуз");
        cities.add("Миасс");
        cities.add("Минеральные Воды");
        cities.add("Минусинск");
        cities.add("Михайловка");
        cities.add("Михайловск (Ставропольский край)");
        cities.add("Мичуринск");
        cities.add("Москва");
        cities.add("Мурманск");
        cities.add("Муром");
        cities.add("Мытищи");
        cities.add("Набережные Челны");
        cities.add("Назарово");
        cities.add("Назрань");
        cities.add("Нальчик");
        cities.add("Наро-Фоминск");
        cities.add("Находка");
        cities.add("Невинномысск");
        cities.add("Нерюнгри");
        cities.add("Нефтекамск");
        cities.add("Нефтеюганск");
        cities.add("Нижневартовск");
        cities.add("Нижнекамск");
        cities.add("Нижний Новгород");
        cities.add("Нижний Тагил");
        cities.add("Новоалтайск");
        cities.add("Новокузнецк");
        cities.add("Новокуйбышевск");
        cities.add("Новомосковск");
        cities.add("Новороссийск");
        cities.add("Новосибирск");
        cities.add("Новотроицк");
        cities.add("Новоуральск");
        cities.add("Новочебоксарск");
        cities.add("Новочеркасск");
        cities.add("Новошахтинск");
        cities.add("Новый Уренгой");
        cities.add("Ногинск");
        cities.add("Норильск");
        cities.add("Ноябрьск");
        cities.add("Нягань");
        cities.add("Обнинск");
        cities.add("Одинцово");
        cities.add("Озерск (Челябинская область)");
        cities.add("Октябрьский");
        cities.add("Омск");
        cities.add("Орел");
        cities.add("Оренбург");
        cities.add("Орехово-Зуево");
        cities.add("Орск");
        cities.add("Павлово");
        cities.add("Павловский Посад");
        cities.add("Пенза");
        cities.add("Первоуральск");
        cities.add("Пермь");
        cities.add("Петрозаводск");
        cities.add("Петропавловск-Камчатский");
        cities.add("Подольск");
        cities.add("Полевской");
        cities.add("Прокопьевск");
        cities.add("Прохладный");
        cities.add("Псков");
        cities.add("Пушкино");
        cities.add("Пятигорск");
        cities.add("Раменское");
        cities.add("Ревда");
        cities.add("Реутов");
        cities.add("Ржев");
        cities.add("Рославль");
        cities.add("Россошь");
        cities.add("Ростов-на-Дону");
        cities.add("Рубцовск");
        cities.add("Рыбинск");
        cities.add("Рязань");
        cities.add("Салават");
        cities.add("Сальск");
        cities.add("Самара");
        cities.add("Санкт-Петербург");
        cities.add("Саранск");
        cities.add("Сарапул");
        cities.add("Саратов");
        cities.add("Саров");
        cities.add("Свободный");
        cities.add("Севастополь");
        cities.add("Северодвинск");
        cities.add("Северск");
        cities.add("Сергиев Посад");
        cities.add("Серов");
        cities.add("Серпухов");
        cities.add("Сертолово");
        cities.add("Сибай");
        cities.add("Симферополь");
        cities.add("Славянск-на-Кубани");
        cities.add("Смоленск");
        cities.add("Соликамск");
        cities.add("Солнечногорск");
        cities.add("Сосновый Бор");
        cities.add("Сочи");
        cities.add("Ставрополь");
        cities.add("Старый Оскол");
        cities.add("Стерлитамак");
        cities.add("Ступино");
        cities.add("Сургут");
        cities.add("Сызрань");
        cities.add("Сыктывкар");
        cities.add("Таганрог");
        cities.add("Тамбов");
        cities.add("Тверь");
        cities.add("Тимашевск");
        cities.add("Тихвин");
        cities.add("Тихорецк");
        cities.add("Тобольск");
        cities.add("Тольятти");
        cities.add("Томск");
        cities.add("Троицк");
        cities.add("Туапсе");
        cities.add("Туймазы");
        cities.add("Тула");
        cities.add("Тюмень");
        cities.add("Узловая");
        cities.add("Улан-Удэ");
        cities.add("Ульяновск");
        cities.add("Урус-Мартан");
        cities.add("Усолье-Сибирское");
        cities.add("Уссурийск");
        cities.add("Усть-Илимск");
        cities.add("Уфа");
        cities.add("Ухта");
        cities.add("Феодосия");
        cities.add("Фрязино");
        cities.add("Хабаровск");
        cities.add("Ханты-Мансийск");
        cities.add("Хасавюрт");
        cities.add("Химки");
        cities.add("Чайковский");
        cities.add("Чапаевск");
        cities.add("Чебоксары");
        cities.add("Челябинск");
        cities.add("Черемхово");
        cities.add("Череповец");
        cities.add("Черкесск");
        cities.add("Черногорск");
        cities.add("Чехов");
        cities.add("Чистополь");
        cities.add("Чита");
        cities.add("Шадринск");
        cities.add("Шали");
        cities.add("Шахты");
        cities.add("Шуя");
        cities.add("Щекино");
        cities.add("Щелково");
        cities.add("Электросталь");
        cities.add("Элиста");
        cities.add("Энгельс");
        cities.add("Южно-Сахалинск");
        cities.add("Юрга");
        cities.add("Якутск");
        cities.add("Ялта");
        cities.add("Ярославль");
        return cities;
    }
}
