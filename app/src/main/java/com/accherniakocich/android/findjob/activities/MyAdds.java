package com.accherniakocich.android.findjob.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.activities.review.RewiewActivity;
import com.accherniakocich.android.findjob.adapters.BoxAdapter;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.social_networks.buy.BuyUpAd;
import com.accherniakocich.android.findjob.social_networks.buy.MarkerAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class MyAdds extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ArrayList <Ad> list;
    private User user;
    private GridView list_view_my_adds;
    private ProgressBar pb_my_adds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adds);
        init();
    }
    private void init() {
        TextView tve = (TextView)findViewById(R.id.empty_view_text);
        tve.setVisibility(View.INVISIBLE);
        user = (User) getIntent().getSerializableExtra("user");
        pb_my_adds = (ProgressBar)findViewById(R.id.pb_my_adds);
        pb_my_adds.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("ads");
        list = new ArrayList<>();
        list_view_my_adds = (GridView)findViewById(R.id.list_view_my_adds);
        list_view_my_adds.setNumColumns(1);
        list_view_my_adds.setHorizontalSpacing(10);
        list_view_my_adds.setVerticalSpacing(10);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue(Ad.class).getUser().getNickName().equals(user.getNickName())){
                    //Log.d(MainActivity.LOG_TAG,"dataSnapshot = "+ dataSnapshot);
                    list.add(dataSnapshot.getValue(Ad.class));
                    BoxAdapter adapter = new BoxAdapter(MyAdds.this,list,null);
                    list_view_my_adds.setAdapter(adapter);
                    pb_my_adds.setVisibility(View.INVISIBLE);
                    tve.setVisibility(View.INVISIBLE);
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

        if (list.size()==0){
            pb_my_adds.setVisibility(View.INVISIBLE);
            tve.setVisibility(View.VISIBLE);
        }

        list_view_my_adds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyAdds.this,Details.class);
                intent.putExtra("ad",list.get(position));
                intent.putExtra("user",user);
                intent.putExtra("fromWhereIntent",2);
                startActivity(intent);
            }
        });
        longClick();
    }

    private void longClick() {
        list_view_my_adds.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAdds.this);
                builder.setView(dialog);
                ((TextView)dialog.findViewById(R.id.dialog_up_ad))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // поднять объявление
                                upAd(list.get(position));
                            }
                        });
                ((TextView)dialog.findViewById(R.id.dialog_seller))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // продано
                                Intent intent = new Intent(MyAdds.this,RewiewActivity.class);
                                intent.putExtra("add",list.get(position));
                                startActivity(intent);
                            }
                        });
                ((TextView)dialog.findViewById(R.id.dialog_marker))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // выделить объявление
                                marker(list.get(position));
                            }
                        });
                builder.show();
                return true;
            }
        });
    }

    private void marker(Ad ad) {
        Intent intent = new Intent(MyAdds.this, MarkerAd.class);
        intent.putExtra("ad",ad);
        startActivity(intent);
    }

    private void upAd(Ad ad) {
        Intent intent = new Intent(MyAdds.this, BuyUpAd.class);
        intent.putExtra("ad",ad);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adds,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh_my_addss) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
