package com.mukmenev.android.findjob.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.activities.log_and_reg.MainActivity;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.Admin;
import com.mukmenev.android.findjob.classes.User;
import com.mukmenev.android.findjob.viewer_images.ViewerImages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {

    //private AppBarLayout app_bar;
    private HorizontalScrollView HSV;
    private Ad ad;
    private TextView name_ad_details,cost_job_detail,text_detais,text_number_of_phone,date_detail,category_details,people_name,adress_detail;
    private ImageView /*fab_delete*/ fab_edit, fab_favorit, image_content_details_1,image_content_details_2,image_content_details_3,image_content_details_4,image_content_details_5,
    indicator_1,indicator_2,indicator_3,indicator_4,indicator_5;
    private User user;
    private FloatingActionButton fab_call,message_detail;
    private String textFavoritList;
    private Admin admin;
    private CircleImageView circle_image_view_people;
    private ScrollView sctoll_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
    }

    private void init() {
        final Intent intent = getIntent();
        ad = (Ad) intent.getSerializableExtra("ad");
        HSV = (HorizontalScrollView) findViewById(R.id.HSV);
        sctoll_details = (ScrollView) findViewById(R.id.sctoll_details);
        message_detail = (FloatingActionButton)findViewById(R.id.message_detail);
        message_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageChat();
            }
        });
        adress_detail  = (TextView)findViewById(R.id.adress_detail);
        people_name = (TextView)findViewById(R.id.people_name);
        circle_image_view_people = (CircleImageView)findViewById(R.id.circle_image_view_people);
        fab_call = (FloatingActionButton) findViewById(R.id.fab_call);
        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!text_number_of_phone.getText().toString().equals("")){
                    if (user!=null){
                        try {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + ad.getPeopleSourceAd()));
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(Details.this,
                                    "К сожалению, пользователь указал неверный номер. Свяжитесь посредством чата", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        category_details = (TextView)findViewById(R.id.category_details);
        name_ad_details = (TextView)findViewById(R.id.name_ad_details);

        cost_job_detail = (TextView) findViewById(R.id.cost_job_detail);
        text_detais = (TextView) findViewById(R.id.text_detais);
        date_detail = (TextView)findViewById(R.id.date_detail);
        fab_favorit = (ImageView) findViewById(R.id.fab_favorit);


        fab_edit = (ImageView) findViewById(R.id.fab_edit);
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Details.this,AddAd.class);
                intent.putExtra("request",1);
                intent.putExtra("ad",ad);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });


//        fab_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                delete();
//            }
//        });



        int fromWhereIntent = intent.getIntExtra("fromWhereIntent",1);
        if (fromWhereIntent==1){
            //fab_delete.setVisibility(View.INVISIBLE);
            fab_edit.setVisibility(View.INVISIBLE);
        }else if (fromWhereIntent==2){
            fab_edit.setVisibility(View.VISIBLE);
        }else if (fromWhereIntent==3){
            // админ просматривает запись
            admin = new Admin("admin@admin.com","admin",new Date().getTime()+"");
        }
        user = (User) intent.getSerializableExtra("user");
        if (ad.getUser()!=null&&!ad.getUser().getImage_path().equals("")){
            Picasso.with(this).load(ad.getUser().getImage_path())
                    .into(circle_image_view_people);
        }

        if (ad!=null&&ad.getUser()!=null){
            User u = ad.getUser();
            if (!u.getName().equals("")){
                people_name.setText(u.getName());
            }else if (!u.getNickName().equals("")){
                people_name.setText(u.getNickName());
            }
        }


        if (admin!=null){

            fab_favorit.setImageResource(R.drawable.turned_24dp);
            fab_edit.setImageResource(R.drawable.ic_grade_black_24dp);
            fab_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("ads")
                            .child(ad.getDateAd()+"")
                            .child("premium")
                            .setValue(true);
                    Snackbar.make(fab_favorit,"Премиум!", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            });
            fab_favorit.setOnClickListener(null);
            fab_favorit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(MainActivity.LOG_TAG,"admin = " +admin);
                    FirebaseDatabase.getInstance().getReference().child("ads")
                            .child(ad.getDateAd()+"")
                            .child("check")
                            .setValue(true);
                    Snackbar.make(fab_favorit,"Объявление одобрено", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            });
        }else{
            fab_favorit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user!=null&&admin==null){
                        // добавляем в избранное
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                                .child("ads")
                                .child(ad.getDateAd()+"")
                                .child("inFavoritUsers");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                textFavoritList = dataSnapshot.getValue()+"";
                                if (!textFavoritList.contains(user.getNickName())){
                                    reference.setValue(textFavoritList + "," +user.getNickName());
                                }else{
                                    reference.setValue(textFavoritList.replace(user.getNickName(),""));
                                    fab_favorit.setImageResource(R.drawable.ic_favorite_black_24dp);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        fab_favorit.setImageResource(R.drawable.ic_favorite_gold_24dp);
                    }
                }
            });
        }


        name_ad_details.setText(ad.getNameJobAd());
        adress_detail.setText(ad.getCity());


        setNumOfShowAd(ad);
        if (ad.getCategoty()!=null&&!ad.getCategoty().equals("")){
            category_details.setText("Категория: " + ad.getCategoty());
        }

        //Log.d(MainActivity.LOG_TAG,"user = "+user);
        isUser(user);

        uploadImages();
        if (user==null||user.getNickName().equals(ad.getUser().getNickName())){
            message_detail.setVisibility(View.INVISIBLE);
        }
        message_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user==null){
                    message_detail.setVisibility(View.INVISIBLE);
                }else{
                    Intent intent_chat = new Intent(Details.this,Dialog.class);
                    intent_chat.putExtra("user_1",user);
                    intent_chat.putExtra("user_2",ad.getUser());
                    startActivity(intent_chat);
                }
            }
        });

        initCost(ad.getCostAd(), ad.getType_money(), cost_job_detail);
        initTextDetails(ad.getTextAd());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("ads")
                .child(ad.getDateAd()+"")
                .child("inFavoritUsers");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (user!=null){
                    if (dataSnapshot.getValue().toString().contains(user.getNickName())){
                        fab_favorit.setImageResource(R.drawable.ic_favorite_gold_24dp);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*share = (Button)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareMethod();
            }
        });*/
    }

    private void messageChat() {
        if (user!=null){

        }
    }

    private void uploadImages() {
        image_content_details_1 = (ImageView) findViewById(R.id.image_content_details_1);
        image_content_details_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ad.getImagePathAd_1().equals("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")){
                    Intent intent = new Intent(Details.this, ViewerImages.class);
                    intent.putExtra("link",ad.getImagePathAd_1());
                    startActivity(intent);
                }
            }
        });
        image_content_details_2 = (ImageView) findViewById(R.id.image_content_details_2);
        image_content_details_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ad.getImagePathAd_2().equals("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")){
                    Intent intent = new Intent(Details.this, ViewerImages.class);
                    intent.putExtra("link",ad.getImagePathAd_2());
                    startActivity(intent);
                }
            }
        });
        image_content_details_3 = (ImageView) findViewById(R.id.image_content_details_3);
        image_content_details_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ad.getImagePathAd_3().equals("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")){
                    Intent intent = new Intent(Details.this, ViewerImages.class);
                    intent.putExtra("link",ad.getImagePathAd_3());
                    startActivity(intent);
                }
            }
        });
        image_content_details_4 = (ImageView) findViewById(R.id.image_content_details_4);
        image_content_details_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ad.getImagePathAd_4().equals("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")){
                    Intent intent = new Intent(Details.this, ViewerImages.class);
                    intent.putExtra("link",ad.getImagePathAd_4());
                    startActivity(intent);
                }
            }
        });
        image_content_details_5 = (ImageView) findViewById(R.id.image_content_details_5);
        image_content_details_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ad.getImagePathAd_5().equals("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")){
                    Intent intent = new Intent(Details.this, ViewerImages.class);
                    intent.putExtra("link",ad.getImagePathAd_5());
                    startActivity(intent);
                }
            }
        });

        Picasso.with(this)
                .load(ad.getImagePathAd_1())
                .into(image_content_details_1);
        Picasso.with(this)
                .load(ad.getImagePathAd_2())
                .into(image_content_details_2);
        Picasso.with(this)
                .load(ad.getImagePathAd_3())
                .into(image_content_details_3);
        Picasso.with(this)
                .load(ad.getImagePathAd_4())
                .into(image_content_details_4);
        Picasso.with(this)
                .load(ad.getImagePathAd_5())
                .into(image_content_details_5);

        changeSizesImages();

        Log.d(MainActivity.LOG_TAG,"patHHH = " + ad.getImagePathAd_1());
    }

    private void changeSizesImages() {

        int wirth = getWidthScreen();

        ViewGroup.LayoutParams layoutParams_1 = image_content_details_1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams_2 = image_content_details_1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams_3 = image_content_details_1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams_4 = image_content_details_1.getLayoutParams();
        ViewGroup.LayoutParams layoutParams_5 = image_content_details_1.getLayoutParams();
        layoutParams_1.width = wirth;
        layoutParams_2.width = wirth;
        layoutParams_3.width = wirth;
        layoutParams_4.width = wirth;
        layoutParams_5.width = wirth;
        image_content_details_1.setLayoutParams(layoutParams_1);
        image_content_details_2.setLayoutParams(layoutParams_2);
        image_content_details_3.setLayoutParams(layoutParams_3);
        image_content_details_4.setLayoutParams(layoutParams_4);
        image_content_details_5.setLayoutParams(layoutParams_5);

        changeIndicators(wirth);
    }


    private void changeIndicators(int width_screen) {
        int scroll_width = width_screen*5;

        sctoll_details.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Rect rectf = new Rect();
                HSV.getLocalVisibleRect(rectf);

                int width = HSV.getWidth();
                int left = rectf.left/4;
            }
        });
    }

    private int getWidthScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    private void delete() {
        new AlertDialog.Builder(Details.this)
                .setTitle("Выход")
                .setMessage("Вы уверены что хотите удалить объявление?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //the user wants to leave - so dismiss the dialog and exit
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ads").child(ad.getDateAd()+"");
                        reference.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                Toast.makeText(Details.this, "Ваше объявление удалено", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // the user is not sure? So you can exit or just stay
                dialog.dismiss();
            }
        }).show();
    }

    private void initTextDetails(String textAd) {
        text_detais.setText(textAd);
    }

    private void initCost(int costAd, String type_money, TextView cost_job_detail) {
        cost_job_detail.setText(costAd+" "+type_money);
    }

    public void isUser(User u) {
        text_number_of_phone = (TextView)findViewById(R.id.text_number_of_phone);
        String outputPattern = "dd/MMM/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = new Date(ad.getDateAd());
        String str = null;
        str = outputFormat.format(date);
        date_detail.setText(str);
        if (u==null){
            //пользователь вошел как Гость
            text_number_of_phone.setText("Гости не могут просматривать контакты. Зарегистрируйтесь!");
            text_number_of_phone.setTextSize(15f);
        }else{
            // вошел зарегистрированный пользователь
            //name_add_generator_ad.setText(ad.getNameAd());
            text_number_of_phone.setText(ad.getPeopleSourceAd());

        }
    }

    private void setNumOfShowAd(Ad adRedact){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ads").child(adRedact.getDateAd()+"").child("numOfShowAd");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(MainActivity.LOG_TAG,"numShows = "+dataSnapshot.getValue());
                String stringIterator = dataSnapshot.getValue().toString();
                int numIterator = Integer.parseInt(stringIterator);
                numIterator++;
                reference.setValue(numIterator);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
