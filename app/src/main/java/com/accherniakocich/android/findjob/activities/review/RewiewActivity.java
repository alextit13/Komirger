package com.accherniakocich.android.findjob.activities.review;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.Ad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RewiewActivity extends AppCompatActivity {

    private Ad ad;
    @BindView(R.id.edit_text_review)EditText edit_text_review;
    @BindView(R.id.button_review_save)Button button_review_save;
    @BindView(R.id.button_review_cancel)Button button_review_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewiew);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        ad = (Ad) getIntent().getSerializableExtra("add");
        clicker();
    }

    private void clicker() {
        button_review_save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edit_text_review.getText().toString().equals("")) firebase();
                    }
                }
        );
        button_review_cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    private void firebase() {
        FirebaseDatabase.getInstance().getReference().child("reviews").child(ad.getPeopleSourceAd())
                .child(new Date().getTime()+"")
                .setValue(
                        edit_text_review.getText().toString()
                ).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RewiewActivity.this,
                                "Ваш отзы добавлен! Благодарим! Команда Комиргер всегда с Вами!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
}