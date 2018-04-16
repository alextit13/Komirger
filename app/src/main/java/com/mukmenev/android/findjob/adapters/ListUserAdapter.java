package com.mukmenev.android.findjob.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListUserAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<User> objects;


    public ListUserAdapter(Context ctx,  ArrayList<User> objects) {
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
            view = lInflater.inflate(R.layout.item_list_users, parent, false);
        }

        User user = getAd(position);

        ((TextView) view.findViewById(R.id.list_users_nick_name)).setText(user.getNickName());

        if (!objects.get(position).getImage_path().equals("")){
            Picasso.with(ctx)
                    .load(objects.get(position).getImage_path())
                    .into((CircleImageView) view.findViewById(R.id.profile_image_list_users));
        }
        return view;
    }

    User getAd(int position) {
        return ((User) getItem(position));
    }
}
