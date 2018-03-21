package com.accherniakocich.android.findjob.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.DialogAdapter;
import com.accherniakocich.android.findjob.classes.Message;
import com.accherniakocich.android.findjob.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dialog extends AppCompatActivity {


    public static final int PICK_IMAGE_REQUEST = 71;


    private User user_I;
    private User user_You;
    private ListView list_messages;
    private ArrayList <Message> list;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private EditText edit_text_message;
    private ImageView button_send_message;

    private CircleImageView circle_image_view_communicator;
    private TextView text_view_communicator;
    private DialogAdapter adapter;
    private ArrayList<Message> mesList;

    private ImageView attach;
    private Uri filePath;

    private String path_image = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        init();
    }

    private void init() {
        list = new ArrayList<>();
        list_messages = (ListView)findViewById(R.id.list_messages);
        user_I = (User) getIntent().getSerializableExtra("user_1"); // я
        user_You = (User) getIntent().getSerializableExtra("user_2"); // собеседник

        attach = (ImageView) findViewById(R.id.attach);

        clicker();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("messages_chat");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue().toString().contains(user_I.getNickName())
                        &&dataSnapshot.getValue().toString().contains(user_You.getNickName())){

                    list.add(dataSnapshot.getValue(Message.class));

                    sortArray(list);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edit_text_message = (EditText)findViewById(R.id.edit_text_message);
        button_send_message = (ImageView) findViewById(R.id.button_send_message);
        button_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage(edit_text_message.getText().toString(),user_I,user_You, new Date().getTime());
            }
        });
        circle_image_view_communicator = (CircleImageView)findViewById(R.id.circle_image_view_communicator);
        if (!user_You.getImage_path().equals("")&&user_You.getImage_path()!=null){
            Picasso.with(this)
                    .load(user_You.getImage_path())
                    .into(circle_image_view_communicator);
        }
        text_view_communicator = (TextView)findViewById(R.id.text_view_communicator);
        text_view_communicator.setText(user_You.getNickName());
    }

    private void clicker() {
        attach.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choseAndDownloadImage();
                    }
                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //profile_image.setImageBitmap(bitmap);
                uploadPhotoToFirebase(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadPhotoToFirebase(Bitmap bitmap) {
        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Загрузка...");
            progressDialog.show();

            StorageReference ref = storageReference.child("users_images/"+ UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            //Log.d(MainActivity.LOG_TAG,"path image = " + taskSnapshot.getDownloadUrl());
                            Toast.makeText(Dialog.this, "Загружено. Нажмите кнопку отправить!", Toast.LENGTH_SHORT).show();
                            path_image = taskSnapshot.getDownloadUrl()+"";
                            /*FirebaseDatabase.getInstance().getReference().child("messages_chat")
                                    .child(date+"").child("uri_download_attach")
                                    .setValue(taskSnapshot.getDownloadUrl()+"");*/
                            attach.setImageResource(R.drawable.have_attach);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Dialog.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void choseAndDownloadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void sortArray(ArrayList<Message> list) {
        Message [] arrMessage = new Message[list.size()];
        for (int i = 0; i<list.size();i++){
            arrMessage[i]=list.get(i);
        }
        Arrays.sort(arrMessage, (a, b) -> a.getDate_message().compareTo(b.getDate_message()));
        mesList = new ArrayList<>(Arrays.asList(arrMessage));
        if (adapter==null){
            adapter = new DialogAdapter(Dialog.this,mesList,user_I);
            list_messages.setAdapter(adapter);
        }else{
            adapter.getData().clear();
            adapter.getData().addAll(mesList);
            list_messages.invalidateViews();
            adapter.notifyDataSetChanged();
        }
    }

    private void sendMessage(String s, User user_i, User user_you, long date) {
        reference
                .child(date+"")
                .setValue(new Message(user_i.getNickName(),user_you.getNickName(),s,
                        date+"",false,path_image));
        edit_text_message.setText("");
        path_image = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_dialog,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh_dialog) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
