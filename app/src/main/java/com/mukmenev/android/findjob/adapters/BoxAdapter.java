package com.mukmenev.android.findjob.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukmenev.android.findjob.R;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.User;
import com.squareup.picasso.Picasso;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Ad> objects;
    User userFromClass;

    public BoxAdapter(Context context, ArrayList<Ad> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BoxAdapter(Context context, ArrayList<Ad> products, User user) {
        userFromClass = user;
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override

    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_list, parent, false);
        }
        Ad ad = getAd(position);

        ((TextView) view.findViewById(R.id.tvText)).setText(ad.getNameJobAd());
        String about_job = ad.getTextAd();
        if (about_job.length()>=30){
            about_job = about_job.substring(0,29)+"..";
        }else{
            about_job = about_job;
        }
        String outputPattern = "dd MMM";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = new Date(ad.getDateAd());
        String str = null;
        str = outputFormat.format(date);
        //((TextView) view.findViewById(R.id.time)).setText(str);

        ((TextView) view.findViewById(R.id.cost_job_main_list_red)).setText(ad.getCostAd()+"");
        ((TextView) view.findViewById(R.id.cost_job_main_list_red_dollar)).setText(" "+ad.getType_money());
        ((TextView) view.findViewById(R.id.about_ad)).setText(" "+about_job);
        //((TextView) view.findViewById(R.id.visions)).setText("Просмотры: "+ad.getNumOfShowAd());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        String date_for_item_data = dateFormat.format(ad.getDateAd());

        ((TextView) view.findViewById(R.id.visions)).setText("Просмотры: "+ad.getNumOfShowAd());

        Picasso.with(ctx)
                .load(ad.getImagePathAd_1())
                .into((ImageView) view.findViewById(R.id.image_from_ad));

        if (ad.getImagePathAd_1()==null||ad.getImagePathAd_1().equals("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")){
            Picasso.with(ctx)
                    .load("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")
                    .into((ImageView) view.findViewById(R.id.image_from_ad));
        }
        if (ad.isPremium()){

            view.setBackgroundColor(Color.parseColor("#e3ffae"));

        }

        return view;
    }

    // товар по позиции
    Ad getAd(int position) {
        return ((Ad) getItem(position));
    }

}