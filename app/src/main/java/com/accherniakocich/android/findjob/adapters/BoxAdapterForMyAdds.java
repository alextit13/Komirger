package com.accherniakocich.android.findjob.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.Ad;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BoxAdapterForMyAdds extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Ad> objects;
    String urlImage;
    private StorageReference mStorageRef;

    BoxAdapterForMyAdds(Context context, ArrayList<Ad> products) {
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
        ((TextView) view.findViewById(R.id.cost_job_main_list_red)).setText(ad.getCostAd()+"");
        ((TextView) view.findViewById(R.id.cost_job_main_list_red_dollar)).setText(" "+ad.getType_money());
        return view;
    }

    // товар по позиции
    Ad getAd(int position) {
        return ((Ad) getItem(position));
    }

}