package com.mukmenev.android.findjob.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.activities.log_and_reg.MainActivity;

import java.util.concurrent.TimeUnit;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                runOnUiThread(()->{
                    ((ProgressBar)findViewById(R.id.splash_screen_progress_bar))
                            .setVisibility(View.GONE);
                    startActivity(new Intent(Splash.this, MainActivity.class));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })
                .start();
    }
}
