package com.accherniakocich.android.findjob.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllUsersAdmin extends AppCompatActivity {

    @BindView(R.id.list_all_users)ListView list_all_users;
    private ArrayList<String>mListUsers = new ArrayList<>();
    private ArrayList<User>mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_admin);
        ButterKnife.bind(this);
        getDataList();
    }

    private void getDataList() {
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            mList.add(data.getValue(User.class));
                            mListUsers.add(data.getValue(User.class).toString());
                        }
                        showList();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void showList() {
        list_all_users.setAdapter(new ArrayAdapter<String>(AllUsersAdmin.this,android.R.layout.simple_list_item_1,mListUsers));
        list_all_users.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mList.get(position).getBlocked()==1){
                            dialog(position,1);
                        }else{
                            dialog(position,2);
                        }

                    }
                }
        );
    }

    private void dialog(int position,int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Заблокировать пользователя");

        if (i==1){
            builder.setMessage("Вы моежете заблокировать пользователя, нажав кнопку ДА");
            builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(mList.get(position).getNickName()).child("blocked")
                            .setValue(2);
                }
            });
        }else if (i==2){
            builder.setMessage("Вы моежете разблокировать пользователя, нажав кнопку ДА");
            builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(mList.get(position).getNickName()).child("blocked")
                            .setValue(1);

                }
            });
        }
        builder.setNegativeButton("НЕТ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.show();
    }
}
