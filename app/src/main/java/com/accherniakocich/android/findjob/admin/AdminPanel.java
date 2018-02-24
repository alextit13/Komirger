package com.accherniakocich.android.findjob.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.accherniakocich.android.findjob.admin.all_user.AllUsers;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.activities.Details;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.Admin;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class AdminPanel extends AppCompatActivity {

    private ListView admin_list;
    private ArrayList <Ad> list_ad;
    private DatabaseReference reference;
    private ProgressBar progress_bar_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        init();
        createSharedAdmin();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_main_list:
                toAllUsers();
        }
        return super.onOptionsItemSelected(item);
    }

    private void toAllUsers() {
        startActivity(new Intent(AdminPanel.this, AllUsers.class));
    }

    private void createSharedAdmin() {
        SaveAdmin mSaveAdmin = new SaveAdmin();
        mSaveAdmin.saveAdmin(this,new Admin("admin@admin.com","admin",new Date().getTime()+""));
    }

    private void init() {
        progress_bar_admin = (ProgressBar)findViewById(R.id.progress_bar_admin);
        admin_list = (ListView)findViewById(R.id.admin_list);
        list_ad = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("ads");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (!dataSnapshot.getValue(Ad.class).isCheck()){
                            list_ad.add(dataSnapshot.getValue(Ad.class));
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
        AdminAdapter adapter = new AdminAdapter(AdminPanel.this,list_ad);
        admin_list.setAdapter(adapter);

        progress_bar_admin.setVisibility(View.INVISIBLE);

        admin_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminPanel.this, Details.class);
                intent.putExtra("ad",list_ad.get(position));
                intent.putExtra("user",new User("admin@admin.com","admin","admin","","",0,false));
                intent.putExtra("fromWhereIntent",3);
                startActivity(intent);
            }
        });
    }
}
