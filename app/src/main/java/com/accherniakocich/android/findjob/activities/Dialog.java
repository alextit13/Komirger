package com.accherniakocich.android.findjob.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.adapters.DialogAdapter;
import com.accherniakocich.android.findjob.classes.Message;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dialog extends AppCompatActivity {

    private User user_I;
    private User user_You;
    private ListView list_messages;
    private ArrayList <Message> list;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private EditText edit_text_message;
    private ImageButton button_send_message;

    private CircleImageView circle_image_view_communicator;
    private TextView text_view_communicator;
    private DialogAdapter adapter;
    private ArrayList<Message> mesList;


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

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("messages_chat");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d(MainActivity.LOG_TAG,"item = " + dataSnapshot.getValue());
                if (dataSnapshot.getValue().toString().contains(user_I.getNickName())
                        &&dataSnapshot.getValue().toString().contains(user_You.getNickName())){
                    /*Log.d(MainActivity.LOG_TAG,"user to = " + dataSnapshot.getValue(Message.class).getName_user_to());
                    Log.d(MainActivity.LOG_TAG,"user I = " + user_I.getNickName());

                    Log.d(MainActivity.LOG_TAG,"user I 2 = " + user_I.getNickName());
                    Log.d(MainActivity.LOG_TAG,"user I 2 = " + user_You.getNickName());
                    */
                    list.add(dataSnapshot.getValue(Message.class));
                    /*for (int i = 0; i<list.size();i++){
                        if (list.get(i).getName_user_to().equals(user_I.getNickName())){
                            //Log.d(MainActivity.LOG_TAG,"yes = " + user_I.getNickName());
                            reference.child(list.get(i).getDate_message())
                                    .child("readOrNot")
                                    .setValue(true);
                        }
                    }*/
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
        button_send_message = (ImageButton)findViewById(R.id.button_send_message);
        button_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long date = new Date().getTime();
                sendMessage(edit_text_message.getText().toString(),user_I,user_You, date);
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

    private void sortArray(ArrayList<Message> list) {
        Message [] arrMessage = new Message[list.size()];
        for (int i = 0; i<list.size();i++){

            arrMessage[i]=list.get(i);
            //System.out.println("early = "+list.get(i).getDate_message());
        }

        //comparator
        /*System.out.println("--- before");
        System.out.println(Arrays.asList(arrMessage));*/
        Arrays.sort(arrMessage, (a, b) -> a.getDate_message().compareTo(b.getDate_message()));
        /*System.out.println("--- after");
        System.out.println(Arrays.asList(arrMessage));*/



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
                .setValue(new Message(user_i.getNickName(),user_you.getNickName(),s,date+"",false));
        edit_text_message.setText("");
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
