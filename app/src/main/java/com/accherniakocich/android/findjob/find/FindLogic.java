package com.accherniakocich.android.findjob.find;

import android.content.Context;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.FindQuestionParameters;
import com.accherniakocich.android.findjob.classes.firebase.GettingDataFromFirebase;
import com.accherniakocich.android.findjob.classes.square_otto.BusStation;
import com.accherniakocich.android.findjob.classes.square_otto.EventAds;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class FindLogic {
    private Context context;
    private FindQuestionParameters mQuestionParameters;
    private ArrayList<Ad>mResultsList;
    private String image_path = "https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb";
    public void toFindQuestionParameters(FindQuestionParameters questionParameters, Context c){
        if (mResultsList!=null){
            mResultsList = null;
        }
        mResultsList = new ArrayList<>();
        context = c;
        mQuestionParameters = questionParameters;
        GettingDataFromFirebase g = new GettingDataFromFirebase();
        g.getListAds(context);
    }
    @Subscribe
    public void takeListData(EventAds list){
        for (Ad ad : list.getProduct()){
            equalsOrNot(ad);
        }
    }

    private void equalsOrNot(Ad ad) {
        if (mQuestionParameters.getTitle().equals("")||ad.getNameJobAd().contains(mQuestionParameters.getTitle())){

            if (mQuestionParameters.isPremium()&&ad.isPremium()||!mQuestionParameters.isPremium()&&!ad.isPremium()){

                if (ad.getCostAd()>mQuestionParameters.getPrice_from()){

                    if (ad.getCostAd()<mQuestionParameters.getPrice_to()){

                        if (mQuestionParameters.isOnly_photo()&&!ad.getImagePathAd_1().equals(image_path)||!mQuestionParameters.isOnly_photo()&&ad.getImagePathAd_1().equals(image_path)){

                            if (ad.getCategoty().contains(mQuestionParameters.getCategory())){

                                if (ad.getCity().contains(mQuestionParameters.getCity())){

                                    mResultsList.add(ad);
                                }
                            }
                        }
                    }
                }
            }
        }
        BusStation.getBus().post(mResultsList);
    }
}
