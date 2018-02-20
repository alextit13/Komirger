package com.accherniakocich.android.findjob.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.BoxAdapter;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Favorit extends AppCompatActivity {

    private GridView grid_view_favorit_adds;
    private User user;
    private ArrayList<Ad>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        user = (User) getIntent().getSerializableExtra("user");
        grid_view_favorit_adds = (GridView)findViewById(R.id.grid_view_favorit_adds);
        gettingListAdds();
        grid_view_favorit_adds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogItem(parent, view, position, id);
            }
        });
    }

    private void gettingListAdds() {
        FirebaseDatabase.getInstance().getReference()
                .child("ads").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Ad.class).getInFavoritUsers().contains(user.getNickName())){
                    list.add(dataSnapshot.getValue(Ad.class));
                    adapter();
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

    private void adapter() {
        BoxAdapter adapter = new BoxAdapter(this,list,user);
        grid_view_favorit_adds.setAdapter(adapter);
    }

    private void showDialogItem(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(Favorit.this,Details.class);
        intent.putExtra("ad",list.get(i));
        intent.putExtra("user",user);
        intent.putExtra("fromWhereIntent",1);
        startActivity(intent);
    }

}
