package com.accherniakocich.android.findjob.adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.Message;
import com.accherniakocich.android.findjob.classes.User;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Message> objects;
    User user;

    public DialogAdapter(Context ctx,  ArrayList<Message> objects,User user) {
        this.user = user;
        this.ctx = ctx;
        this.objects = objects;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_dialog_message, parent, false);
        }

        Message message = getAd(position);

        ((TextView) view.findViewById(R.id.dialog_name_user_from)).setText(message.getName_user_from());
        String little_text_message = "";
        little_text_message = message.getText_message();

        TextView tv_1 = (TextView) view.findViewById(R.id.dialog_little_text_of_message);
        tv_1.setText(little_text_message);


        long d = Long.parseLong(message.getDate_message());
        String dateString = new SimpleDateFormat("dd.MM").format(new Date(d));
        TextView tv_2 = (TextView) view.findViewById(R.id.dialog_date);
        tv_2.setText(dateString);

        LinearLayout ll= (LinearLayout)view.findViewById(R.id.message_container);
        LinearLayout lls= (LinearLayout)view.findViewById(R.id.subparent);



        Log.d(MainActivity.LOG_TAG,"message.getName_user_to() = " + message.getName_user_to());
        Log.d(MainActivity.LOG_TAG,"user = " + user.getNickName());



        if (message.getName_user_to().equals(user.getNickName()) &&!message.isReadOrNot()){
            Log.d(MainActivity.LOG_TAG,"change to true");
            FirebaseDatabase.getInstance().getReference().child("messages_chat").child(message.getDate_message()+"").child("readOrNot").setValue(true);
            // если сообщение пришло нам и оно не прочитано - то мы делаем его прочитанным
        }



        ImageView imageReadMessageOrNotReade = (ImageView)view.findViewById(R.id.read_or_not_read_message);
        if (!message.getName_user_to().equals(user.getNickName())
                &&message.isReadOrNot()){
            // ставим галочку что прочитано, сообщение пошло другому пользователю и было прочитано

            imageReadMessageOrNotReade.setImageResource(R.drawable.ic_check_black_24dp);
            imageReadMessageOrNotReade.setAlpha(1f);
        }else{
            // галочку убираем
            imageReadMessageOrNotReade.setAlpha(0f);
        }

        if (!message.getName_user_from().equals(user.getNickName())){
            lls.setBackgroundResource(R.drawable.round_my_message);
            ll.setGravity(Gravity.LEFT);
        }else{
            lls.setBackgroundResource(R.drawable.round_your_message);
            ll.setGravity(Gravity.RIGHT);
            //message is my
        }
        return view;
    }

    public ArrayList<Message> getData() {
        return objects;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    Message getAd(int position) {
        return ((Message) getItem(position));
    }

}
