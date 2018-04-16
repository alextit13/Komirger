package com.accherniakocich.android.findjob.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.BoxAdapter;
import com.accherniakocich.android.findjob.admin.classes.PremAd;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.services.ServiceNotificationUsers;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class MainList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ProgressBar pr_b;
    private ListView list;
    private ArrayList <Ad> listAd;
    private ArrayList <PremAd> listPremium = new ArrayList<>();
    private FirebaseAuth mAuth;
    private User user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private StorageReference mStorageRef;
    private ImageView navigation_home,navigation_licke,navigation_add,navigation_message,navigation_private_room,find_main_screen;
    private BoxAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
        startServiceNotification();
    }

    private void startServiceNotification() {
        startService(new Intent(this,ServiceNotificationUsers.class));
    }

    private void init() {
        find_main_screen = (ImageView)findViewById(R.id.find_main_screen);
        find_main_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainList.this,Find.class);
                intent.putExtra("list",listAd);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        initDrawer();
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-8152940176557798~4343674587");
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");// если тут пришел null то мы вошли как гость
        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        listAd = new ArrayList<>();
        list = (ListView) findViewById(R.id.list);
        pr_b = (ProgressBar)findViewById(R.id.pr_b);
        downlodList("ВСЕ КАТЕГОРИИ");
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainList.this,Details.class);
                intent.putExtra("ad",listAd.get(position));
                intent.putExtra("user",user);
                intent.putExtra("fromWhereIntent",1);
                startActivity(intent);
            }
        });
    }

    private void initDrawer() {
        navigation_home = (ImageView)findViewById(R.id.navigation_home);
        navigation_licke = (ImageView)findViewById(R.id.navigation_licke);
        navigation_add = (ImageView)findViewById(R.id.navigation_add);
        navigation_message = (ImageView)findViewById(R.id.navigation_message);
        navigation_private_room = (ImageView)findViewById(R.id.navigation_private_room);
        navigation_home.setBackgroundColor(Color.parseColor("#FFBABABA"));

        navigation_licke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user==null){
                    Toast.makeText(getApplicationContext(), "У гостей нет избранного!", Toast.LENGTH_SHORT).show();
                }else{
                    //add ad
                    Intent intent = new Intent(MainList.this,Favorit.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });
        navigation_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user==null){
                    Toast.makeText(getApplicationContext(), "Гости не могут подать объявление! Зарегистрируйтесь", Toast.LENGTH_SHORT).show();
                }else{
                    //add ad
                    Intent intent = new Intent(MainList.this,AddAd.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });
        navigation_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user!=null){
                    Intent intent = new Intent(MainList.this,ListUsers.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });
        navigation_private_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user==null){
                    Toast.makeText(getApplicationContext(), "У гостей нет личного кабинета!", Toast.LENGTH_SHORT).show();
                }else{
                    //add ad
                    Intent intent = new Intent(MainList.this,PrivateRoom.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });
    }

    private void downlodList(String category_download_list){
        reference.child("ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAd.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if (postSnapshot.getValue(Ad.class).isCheck()){
                        if (category_download_list.equals("ВСЕ КАТЕГОРИИ")){
                            listAd.add(postSnapshot.getValue(Ad.class));
                        }else if (postSnapshot.getValue(Ad.class).getCategoty().equals(category_download_list)){
                            listAd.add(postSnapshot.getValue(Ad.class));
                        }
                    }
                }
                sortPremiumAds();
                adapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sortPremiumAds() {
        for (int i = 0; i<listAd.size();i++){
            if (listAd.get(i).isPremium()){
                listAd.add(0,listAd.get(i));
                listAd.remove(i+1);
            }
        }
    }

    private void adapter(){
        //getPremiumAds();
        if (listAd.size()!=0){
            if (user==null){
                adapter = new BoxAdapter(this,listAd);
            }else{
                adapter = new BoxAdapter(this,listAd,user);
            }
            pr_b.setVisibility(View.INVISIBLE);
            list.setAdapter(adapter);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new AlertDialog.Builder(MainList.this)
                .setTitle("Выход")
                .setMessage("Вы уверены что хотите выйти?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //the user wants to leave - so dismiss the dialog and exit
                        finish();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // the user is not sure? So you can exit or just stay
                dialog.dismiss();
            }
        }).show();
        //return super.onKeyDown(keyCode, event);
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void mGoToCategory(String category){
        Intent intent = new Intent(MainList.this,ActivityContainer.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.all_ads:downlodList("ВСЕ КАТЕГОРИИ");break;
            case R.id.all_company:mGoToCategory("Все компании");break;
            case R.id.farm_1:mGoToCategory("Молоко");break;
            case R.id.farm_2:mGoToCategory("Яйца");break;
            case R.id.farm_3:mGoToCategory("Овощи");break;
            case R.id.farm_4:mGoToCategory("Фрукты");break;
            case R.id.farm_5:mGoToCategory("Инвентарь");break;
            case R.id.farm_6:mGoToCategory("Другое");break;
            case R.id.job:mGoToCategory("Работа");break;
            case R.id.houses:mGoToCategory("Недвижимость");break;
            case R.id.ladies_garderobe:mGoToCategory("Женский гардероб");break;
            case R.id.mens_garderobe:mGoToCategory("Мужской гардероб");break;
            case R.id.baby_garderobe:mGoToCategory("Детский гардероб");break;
            case R.id.baby_products:mGoToCategory("Детские товары");break;
            case R.id.handmade:mGoToCategory("Хендмейд");break;
            case R.id.auto_and_moto:mGoToCategory("Авто и мото");break;
            case R.id.phones_and_tablets:mGoToCategory("Телефоны и планшеты");break;
            case R.id.photo_and_videocameras:mGoToCategory("Фото и видеокамеры");break;
            case R.id.computers:mGoToCategory("Компьютеры");break;
            case R.id.electronics:mGoToCategory("Электроника и бытовая техника");break;
            case R.id.for_home_for_dacha:mGoToCategory("Для дома для дачи");break;
            case R.id.repair_and_building:mGoToCategory("Ремонт и строительство");break;
            case R.id.beauterfool:mGoToCategory("Красота и здоровье");break;
            case R.id.sport_and_relax:mGoToCategory("Спорт и отдых");break;
            case R.id.hobbies:mGoToCategory("Хобби и развлечения");break;
            case R.id.animals:mGoToCategory("Животные");break;
            case R.id.for_business:mGoToCategory("Для бизнеса");break;
            case R.id.another_category:mGoToCategory("Прочее");break;
            default:break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
