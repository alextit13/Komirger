package com.accherniakocich.android.findjob.activities.log_and_reg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.MainList;
import com.accherniakocich.android.findjob.admin.AdminPanel;
import com.accherniakocich.android.findjob.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LoginUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar progress_bar;
    private FrameLayout container_frame_log_in;

    private EditText et_email;
    private EditText et_password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        init();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out

                }
                // ...
            }
        };
    }

    private void init() {
        et_email = (EditText)findViewById(R.id.et_email_log_in);
        et_password = (EditText)findViewById(R.id.et_password_log_in);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        container_frame_log_in = (FrameLayout)findViewById(R.id.container_frame_log_in);
        progress_bar.setVisibility(View.INVISIBLE);

        //checkSharedPrefs();
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

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_sign_in:
                finish();
                break;
            case R.id.sign_in:
                container_frame_log_in.setAlpha(0.3f);
                progress_bar.setVisibility(View.VISIBLE);
                if (et_email.getText().toString().equals("admin@admin.com")){
                    sign_in_admin();
                }else{
                    sign_in(et_email.getText().toString(),et_password.getText().toString());
                }
        }
    }

    private void sign_in_admin() {
        Intent intent = new Intent(LoginUser.this, AdminPanel.class);
        startActivity(intent);
    }

    private void sign_in(final String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginUser.this, "Вы успешно вошли!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginUser.this,MainList.class);
                    String nickName = email.substring(0,email.indexOf("@",0));
                    user = new User(mAuth.getCurrentUser().getEmail(),nickName,"","","Обо мне",3,false,1);
                    createSharedPreference(user);
                    intent.putExtra("user",user);
                    checkBlocked(intent);
                }else{
                    Toast.makeText(LoginUser.this, "Ошибка входа", Toast.LENGTH_SHORT).show();
                    container_frame_log_in.setAlpha(1f);
                    progress_bar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void checkBlocked(Intent intent) {
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getNickName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(User.class).getBlocked()==1){
                    container_frame_log_in.setAlpha(1f);
                    progress_bar.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                }else if(dataSnapshot.getValue(User.class).getBlocked()==2){
                    Toast.makeText(LoginUser.this, "Вы заблокированы", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
