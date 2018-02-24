package com.accherniakocich.android.findjob.admin;

import android.content.Context;
import android.util.Log;

import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.classes.Admin;
import com.accherniakocich.android.findjob.classes.User;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class SaveAdmin {
    private Context context;
    public void saveAdmin(Context c, Admin a){
        context = c;
        new Thread(new Runnable() {
            @Override
            public void run() {
                createSharedPreference(a);
            }
        })
                .start();
    }
    private void createSharedPreference(Admin mAdmin) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput("FILENAME", MODE_PRIVATE)));
            // пишем данные
            Gson gson = new Gson();
            String json = gson.toJson(mAdmin);
            bw.write(json);
            bw.close();
            Log.d(MainActivity.LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}