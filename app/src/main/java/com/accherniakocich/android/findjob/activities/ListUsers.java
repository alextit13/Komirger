package com.accherniakocich.android.findjob.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.ListUserAdapter;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.classes.maps_location.LocationService;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListUsers extends AppCompatActivity {

    private ListView list_view_list_users;
    private ArrayList <User> list_users;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        init();
    }

    private void init() {
        list_view_list_users = (ListView)findViewById(R.id.list_view_list_users);
        list_view_list_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListUsers.this,Dialog.class);
                intent.putExtra("user_1",user); // i
                intent.putExtra("user_2",list_users.get(position)); // собеседник
                startActivity(intent);
            }
        });
        list_users = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        reference= database.getReference().child("users");
        user = (User) getIntent().getSerializableExtra("user");

        downloadListUsers(reference);
    }

    private void downloadListUsers(DatabaseReference ref) {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.getValue(User.class).getNickName().equals(user.getNickName())){
                    list_users.add(dataSnapshot.getValue(User.class));
                    adapter(list_users);
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
    }

    private void adapter(ArrayList<User>list) {
        ListUserAdapter adapter = new ListUserAdapter(this,list);
        list_view_list_users.setAdapter(adapter);
    }
}
