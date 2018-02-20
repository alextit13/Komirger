package com.accherniakocich.android.findjob.edit_profile;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class EditProfile extends AppCompatActivity {

    private EditText et_edit_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    private void init() {
        et_edit_name = (EditText) findViewById(R.id.et_edit_name);
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
            if (!et_edit_name.getText().toString().equals("")){
                FirebaseDatabase.getInstance().getReference().child("users")
                        .child(user.getNickName()).child("name")
                        .setValue(et_edit_name.getText().toString());
                user.setName(et_edit_name.getText().toString());
                createSharedPreference(user);
                Snackbar.make((Button)findViewById(R.id.edit_name_save),"Данные будут изменены",Snackbar.LENGTH_SHORT).show();
                finish();
            }else{
                Snackbar.make((Button)findViewById(R.id.edit_name_save),"Введите имя",Snackbar.LENGTH_SHORT).show();
            }
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
    }

}
