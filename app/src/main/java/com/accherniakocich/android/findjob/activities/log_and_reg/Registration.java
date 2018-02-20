package com.accherniakocich.android.findjob.activities.log_and_reg;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.MainList;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.UserSingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar progress_bar_registration;
    private FrameLayout container_frame_registration;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private EditText et_email;
    private EditText et_password;
    private EditText et_fio;
    private ImageView info_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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
        et_email = (EditText)findViewById(R.id.et_email);
        et_fio = (EditText)findViewById(R.id.et_fio);
        et_password = (EditText)findViewById(R.id.et_password);
        progress_bar_registration = (ProgressBar)findViewById(R.id.progress_bar_registration);
        container_frame_registration = (FrameLayout)findViewById(R.id.container_frame_registration);
        progress_bar_registration.setVisibility(View.INVISIBLE);
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        info_registration = (ImageView)findViewById(R.id.info_registration);
        info_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Registration.this)
                        .setTitle("Информация")
                        .setMessage("Указывайте e-mail в виде 'example@example.com'. Пароль должен состоять из цифр и букв латинского алфавита")
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //the user wants to leave - so dismiss the dialog and exit
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.reg:

                container_frame_registration.setAlpha(0.3f);
                container_frame_registration.setClickable(false);
                progress_bar_registration.setVisibility(View.VISIBLE);
                registration(et_email.getText().toString(),et_password.getText().toString(),et_fio.getText().toString());
        }
    }

    private void registration(final String email, String password,String name) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // cool!
                    Toast.makeText(Registration.this, "Вы успешно зарегистрировались!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registration.this,MainList.class);
                    String nickName = email.substring(0,email.indexOf("@",0));
                    User user = new User(mAuth.getCurrentUser().getEmail(),nickName,et_fio.getText().toString()
                    ,"https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb",
                            "Обо мне",3,false);
                    UserSingleton.setUser(user);
                    reference.child(nickName).setValue(user);

                    createSharedPreference(user);

                    intent.putExtra("user",user);
                    startActivity(intent);
                }else{
                    //break(
                    progress_bar_registration.setVisibility(View.INVISIBLE);
                    container_frame_registration.setAlpha(1f);
                    container_frame_registration.setClickable(true);
                    Toast.makeText(Registration.this, "Ошибка регистрации", Toast.LENGTH_LONG).show();
                }
            }
        });
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
