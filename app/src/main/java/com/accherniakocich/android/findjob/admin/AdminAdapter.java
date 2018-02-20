package com.accherniakocich.android.findjob.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.R;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdminAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Ad> objects;
    String urlImage;
    private StorageReference mStorageRef;
    String textFavoritList;
    int adFromMyAdds;
    ImageView edit_ad_private_room;

    public AdminAdapter(Context context, ArrayList<Ad> products) {
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

        return 1;
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


        //((ImageView)view.findViewById(R.id.eye)).setVisibility(View.INVISIBLE);

        String outputPattern = "dd MMM";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = new Date(ad.getDateAd());
        String str = null;
        str = outputFormat.format(date);
        //((TextView) view.findViewById(R.id.time)).setText(str);

        ((TextView) view.findViewById(R.id.cost_job_main_list_red)).setText(ad.getCostAd()+"");
        ((TextView) view.findViewById(R.id.cost_job_main_list_red_dollar)).setText(" "+ad.getType_money());
        ((TextView) view.findViewById(R.id.about_ad)).setText(" "+about_job);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        String date_for_item_data = dateFormat.format(ad.getDateAd());

        //((TextView) view.findViewById(R.id.item_data)).setText(date_for_item_data);


        Picasso.with(ctx)
                .load(ad.getImagePathAd_1())
                .into((ImageView) view.findViewById(R.id.image_from_ad));

        if (ad.getImagePathAd_1()==null||ad.getImagePathAd_1().equals("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")){
            Picasso.with(ctx)
                    .load("https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb")
                    .into((ImageView) view.findViewById(R.id.image_from_ad));
        }

        //Log.d(MainActivity.LOG_TAG,"image = " + mStorageRef+ad.getImagePathAd().substring(31));
        //Log.d(MainActivity.LOG_TAG,"url = " + ad.getImagePathAd());
        //ImageView add_to_favorit = (ImageView)view.findViewById(R.id.add_to_favorit);



        return view;
    }

    // товар по позиции
    Ad getAd(int position) {
        return ((Ad) getItem(position));
    }

}