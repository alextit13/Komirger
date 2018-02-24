package com.accherniakocich.android.findjob.admin.all_user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.ListUserAdapter;
import com.accherniakocich.android.findjob.admin.interfaces.TakeAllUsers;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllUsers extends Activity{
    @BindView(R.id.list_all_user_admin)ListView list_all_user_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        ButterKnife.bind(this);
        takeAllUser();
        addToListListener();
    }

    private void addToListListener() {

    }

    private void takeAllUser() {
        ArrayList<User>mListUsers = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("users")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    mListUsers.add(data.getValue(User.class));
                                }
                                completeAdapter(mListUsers);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }

    private void completeAdapter(ArrayList<User> mListUsers) {
        ListUserAdapter adapter = new ListUserAdapter(this,mListUsers);
        list_all_user_admin.setAdapter(adapter);
    }
}
