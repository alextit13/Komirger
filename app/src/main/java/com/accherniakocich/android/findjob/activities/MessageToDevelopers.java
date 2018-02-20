package com.accherniakocich.android.findjob.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;

public class MessageToDevelopers extends AppCompatActivity {

    private EditText edit_text_message_to_developers;
    private Button button_message_to_developers_cancel,button_message_to_developers_send;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_developers);

        init();
    }

    private void init() {

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user_send_message_to_developers");

        edit_text_message_to_developers = (EditText)findViewById(R.id.edit_text_message_to_developers);

        button_message_to_developers_cancel = (Button)findViewById(R.id.button_message_to_developers_cancel);
        button_message_to_developers_send = (Button)findViewById(R.id.button_message_to_developers_send);

        button_message_to_developers_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MessageToDevelopers.this)
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
            }
        });
        button_message_to_developers_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_text_message_to_developers.getText()!=null){
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",
                            "torriodorfindjobapplication@gmail.com",
                            null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Приложение Kommirger");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, edit_text_message_to_developers.getText().toString());
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));


                    edit_text_message_to_developers.setText("");
                    Toast.makeText(MessageToDevelopers.this, "Сообщение отпралено!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
