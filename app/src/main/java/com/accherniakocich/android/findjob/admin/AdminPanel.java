package com.accherniakocich.android.findjob.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.accherniakocich.android.findjob.admin.fragments.AllAds;
import com.accherniakocich.android.findjob.admin.fragments.PremiumAdmin;
import com.accherniakocich.android.findjob.admin.interfaces.GetDataFromFirebase;
import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.activities.Details;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {
    private ListView admin_list;
    private ArrayList <Ad> list_ad = new ArrayList<>();
    private ProgressBar progress_bar_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        init();
        clicker();
    }

    private void init() {
        admin_list_fragment();
    }

    private void admin_list_fragment() {
        AllAds allAds = new AllAds();
        getFragmentManager().beginTransaction().add(R.id.container_admin,allAds)
                .addToBackStack("all_ads")
                .commit();
    }

    private void clicker() {
        ((ImageView)findViewById(R.id.admin_all_users))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this,AllUsersAdmin.class));
            }
        });
        ((ImageView)findViewById(R.id.premium))
                .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toFragments();
                    }
                }
        );
    }

    private void toFragments() {
        PremiumAdmin premiumAdmin = new PremiumAdmin();
        getFragmentManager().beginTransaction().replace(R.id.container_admin,premiumAdmin)
                .addToBackStack("premium")
                .commit();
    }

    private void adapter() {
        /*AdminAdapter adapter = new AdminAdapter(AdminPanel.this,list_ad);
        admin_list.setAdapter(adapter);

        progress_bar_admin.setVisibility(View.INVISIBLE);

        admin_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminPanel.this, Details.class);
                intent.putExtra("ad",list_ad.get(position));
                intent.putExtra("user",new User("admin@admin.com","admin","admin","","",0,false,1));
                intent.putExtra("fromWhereIntent",3);
                startActivity(intent);
            }
        });*/
    }
}
