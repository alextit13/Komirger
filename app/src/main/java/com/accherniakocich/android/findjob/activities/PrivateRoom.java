package com.accherniakocich.android.findjob.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.accherniakocich.android.findjob.edit_profile.EditProfile;
import com.accherniakocich.android.findjob.social_networks.vk.BuyPremium;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateRoom extends AppCompatActivity {
    private FrameLayout container_fragment;
    public static final int PICK_IMAGE_REQUEST = 71;
    private CircleImageView profile_image;
    private User user;
    private String link;
    private Uri filePath;
    private TextView name_user_private_room,email_user_private_room,log_in_user_private_room;
    private Button message_to_developers_button,delete_user,buy_premium_account;
    @BindView(R.id.rating_private_room)RatingBar rating_private_room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_room);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        message_to_developers_button = (Button)findViewById(R.id.message_to_developers_button);
        delete_user = (Button)findViewById(R.id.delete_user);
        buy_premium_account = (Button)findViewById(R.id.buy_premium_account);
        container_fragment = (FrameLayout)findViewById(R.id.container_fragment);
        profile_image  =(CircleImageView)findViewById(R.id.profile_image);

        name_user_private_room = (TextView)findViewById(R.id.name_user_private_room);
        email_user_private_room = (TextView)findViewById(R.id.email_user_private_room);
        log_in_user_private_room = (TextView)findViewById(R.id.log_in_user_private_room);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseAndDownloadImage();
            }
        });
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(user.getNickName()).child("image_path").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    link = dataSnapshot.getValue().toString();
                    Picasso.with(PrivateRoom.this)
                            .load(link)
                            .into(profile_image);
                }catch (Exception e){
                    // Log...
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d(MainActivity.LOG_TAG,"name = " + user.getName());

        name_user_private_room.setText(user.getName());
        email_user_private_room.setText(user.getEmail());
        log_in_user_private_room.setText(user.getNickName());
        Log.d("log","user rating = " + user.getRating());
        if (user.getRating()!=0){
            rating_private_room.setRating(user.getRating());
        }
    }

    private void choseAndDownloadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
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
                            Toast.makeText(PrivateRoom.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference().child("users").child(user.getNickName()).child("image_path")
                                    .setValue(taskSnapshot.getDownloadUrl()+"");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PrivateRoom.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_private_room,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_profile_user) {
            editProfile();
            return true;
        }else if(id == R.id.my_ads){
            Intent intent = new Intent(PrivateRoom.this,MyAdds.class);
            intent.putExtra("user",user);
            startActivity(intent);
            return true;
        }else if(id == R.id.my_not_active_ads){
            Intent intent = new Intent(PrivateRoom.this,OnModerate.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void editProfile() {
        Intent intent = new Intent(PrivateRoom.this, EditProfile.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    private void sendMessageToDevelopers(){
        Intent intent = new Intent(PrivateRoom.this,MessageToDevelopers.class);
        intent.putExtra("user_send_message_to_developers",user);
        startActivity(intent);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.message_to_developers_button:
                //
                sendMessageToDevelopers();
                break;
            case R.id.delete_user:
                //
                deleteAccount(user);
                break;
            case R.id.buy_premium_account:
                //
                buyPremiumAccount();
                Snackbar.make(buy_premium_account,"Функция в разработке!",Snackbar.LENGTH_SHORT).show();
                break;
                    default:
                        break;
        }
    }

    private void deleteAccount(User U) {
        Log.d(MainActivity.LOG_TAG,"user = " + user.getName());
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Удаление");
        dialog.setMessage("Удалить свой аккаунт?");
        dialog.setPositiveButton("Удалить",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(user.getNickName())
                                    .removeValue()
                                        .addOnCompleteListener(
                                                new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(PrivateRoom.this, "Ваш аккаунт удален", Toast.LENGTH_SHORT).show();
                                                        createSharedPreference(null);
                                                        Intent intent = new Intent(PrivateRoom.this,MainActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    }
                                                }
                                        );
                    }
                });
        dialog.setNegativeButton("Нет",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
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

    private void buyPremiumAccount() {
        Intent intent = new Intent(PrivateRoom.this, BuyPremium.class);
        startActivity(intent);
    }
}
