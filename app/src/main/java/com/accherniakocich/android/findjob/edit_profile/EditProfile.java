package com.accherniakocich.android.findjob.edit_profile;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfile extends AppCompatActivity {

    private EditText et_edit_name;
    @BindView(R.id.my_locate)CheckBox my_locate;
    @BindView(R.id.et_about_me)EditText et_about_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        et_edit_name = (EditText) findViewById(R.id.et_edit_name);
        try {
            et_edit_name.setText(((User)getIntent().getSerializableExtra("user")).getName());
            et_about_me.setText(((User)getIntent().getSerializableExtra("user")).getAbout_me());
            my_locate.setChecked(((User)getIntent().getSerializableExtra("user")).isMy_locate());
        }catch (Exception e){
           // Log ...
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_name_cancel:
                finish();
                break;
            case R.id.edit_name_save:
                try {
                    saveName((User)getIntent().getSerializableExtra("user"));
                }catch (Exception e){
                    /**
                     * Exception...
                     * */
                }
                break;
            default:
                break;
        }
    }

    private void saveName(User user) {
        if (user!=null){

            if (my_locate.isChecked()){
                user.setMy_locate(true);
            }else{
                user.setMy_locate(false);
            }
            user.setAbout_me(et_about_me.getText().toString());
            user.setName(et_edit_name.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("users").child(user.getNickName())
                    .setValue(user).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            createSharedPreference(user);
                        }
                    }
            );
        }
    }

    private void createSharedPreference(User user) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput("FILENAME", MODE_PRIVATE)));
            // пишем данные
            Gson gson = new Gson();
            String json = gson.toJson(user);


            bw.write(json);
            // закрываем поток
            bw.close();
            Log.d(MainActivity.LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }

}
