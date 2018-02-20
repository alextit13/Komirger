package com.accherniakocich.android.findjob.activities.log_and_reg;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.MainList;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.fragments.RegistrationFragments;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "MyLogs";
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIsDelete();
        checkSharedPreference();
    }

    private void checkIsDelete() {

        Intent intent = getIntent();
        String delete = null;
        delete = intent.getStringExtra("delete_account");
        //Log.d(MainActivity.LOG_TAG,"delete = " + intent.getStringExtra("delete_account"));
        if (delete!=null){
            if (intent.getStringExtra("delete_account").equals("delete")&&(User) intent.getSerializableExtra("user")!=null){
                try {
                    // отрываем поток для записи
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                            openFileOutput("FILENAME", MODE_PRIVATE)));
                    // пишем данные
                    Gson gson = new Gson();
                    String json = gson.toJson(null);

                    bw.write(json);
                    // закрываем поток
                    bw.close();
                    Log.d(MainActivity.LOG_TAG, "Файл записан");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                User u = (User) intent.getSerializableExtra("user");

                FirebaseDatabase.getInstance().getReference()
                        .child("users").child(u.getNickName()).removeValue();
            }
        }
    }

    private void checkSharedPreference() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput("FILENAME")));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
                Gson gson = new Gson();
                user = gson.fromJson(str, User.class);
            }

            if (user!=null){
                Log.d(MainActivity.LOG_TAG,"user = " + user);
                Intent intent = new Intent(MainActivity.this,MainList.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_sign_in_how_quest:
                //вход как гость
                Intent intent = new Intent(MainActivity.this,MainList.class);
                startActivity(intent);
                break;
            case R.id.sign_in_user:
                //вход как физ лицо
                sign_in();
                break;
            case R.id.registration_fiz:
                // регистрация физ лица
                registration_fiz();
                break;
            case R.id.reg_yur:
                // регистрация юр лица - фирмы
                registration_yur();
            default:
                break;
        }
    }

    private void registration_yur() {
        startActivity(new Intent(MainActivity.this,ActivityContainerReg.class));
    }

    private void sign_in() {
        //вход как физ лицо
        Intent intent = new Intent(MainActivity.this,LoginUser.class);
        startActivity(intent);
    }

    private void registration_fiz() {
        //регистрация физ лица
        Intent intent = new Intent(MainActivity.this,Registration.class);
        startActivity(intent);
    }
}
