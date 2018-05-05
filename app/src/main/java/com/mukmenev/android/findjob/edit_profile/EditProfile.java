package com.mukmenev.android.findjob.edit_profile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mukmenev.android.findjob.activities.log_and_reg.MainActivity;
import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.LocateUser;
import com.mukmenev.android.findjob.classes.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

                }
                break;
            default:
                break;
        }
    }

    private void saveName(User user) {
        if (user!=null){

            if (my_locate.isChecked()){
                enebledMyLocate();
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

    private void enebledMyLocate() {
        String nameUser = (et_edit_name.getText().toString());
        /*Locate locate = new Locate();
        locate.getLocation(EditProfile.this,nameUser);*/
    }

    /*public static class Locate extends AppCompatActivity{
        Context context;
        private static final int REQUEST_CODE_PERMISSION = 2;
        String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        FusedLocationProviderClient mFusedLocationClient;

        private void takePermission() {
            try {
                if (ActivityCompat.checkSelfPermission(context, mPermission)
                        != MockPackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getParent(), new String[]{mPermission, Manifest.permission.READ_PHONE_STATE},
                            REQUEST_CODE_PERMISSION);
                }else{
                    //read location
                    //getLocation();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public Map<String, double[]> getLocation(Context c,String userName){

            context = c;
            takePermission();

            Map<String,double[]>location = new HashMap<>();
            String key = userName;
            double[]coordinates = new double[2];

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(c);
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }else{
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    //Log.d(VARIABLES_CLASS.LOG_TAG,"location = " + location);
                                    Geocoder geocoder = new Geocoder(c, Locale.getDefault());
                                    List<Address> addresses = null;
                                    try {
                                        addresses = geocoder.getFromLocation(
                                                location.getLatitude(),
                                                location.getLongitude(),
                                                // In this sample, get just a single address.
                                                1);
                                        coordinates[0]=location.getLongitude();
                                        coordinates[1]=location.getLatitude();

                                        pushToFirebaseCoordinates(coordinates);

                                        Log.d("coordinates", "lon = " + coordinates[0]);
                                        Log.d("coordinates", "lat = " + coordinates[1]);

                                    } catch (IOException e) {

                                    }
                                }
                            }
                        });
            }
            location.put(key,coordinates);
            return location;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("coordinates","permission");
                    } else {
                        System.out.println("permission denied!");
                    }
                    break;
            }
        }

    }*/

    /*private void pushToFirebaseCoordinates(double[]coordinates) {
        User user = (User)getIntent().getSerializableExtra("user");
        String userName = user.getNickName();
        Map<String,double[]> mapLocation = new HashMap<>();
        mapLocation.put(userName,coordinates);
        double[]c = new double[2];
        c= mapLocation.get(userName);
        LocateUser userLocate = new LocateUser(userName, mapLocation);

        FirebaseDatabase.getInstance().getReference().child("location_users")
                .child(userName)
                .child("lat")
                .setValue(userLocate.getLat())
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Toast.makeText(EditProfile.this, "Местоположение сохранено", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        FirebaseDatabase.getInstance().getReference().child("location_users")
                .child(userName)
                .child("lon")
                .setValue(userLocate.getLon())
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EditProfile.this, "Местоположение сохранено", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                );
    }*/
}
