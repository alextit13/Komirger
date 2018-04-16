package com.mukmenev.android.findjob.find;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.mukmenev.android.findjob.activities.FindResult;
import com.mukmenev.android.findjob.classes.Ad;
import com.mukmenev.android.findjob.classes.FindQuestionParameters;
import com.mukmenev.android.findjob.classes.User;

import java.util.ArrayList;

public class FindLogic {
    private User user;
    private Activity activity;
    private FindQuestionParameters mQuestionParameters;
    private ArrayList<Ad>mResultsList;
    private ArrayList<Ad>list = new ArrayList<>();
    private String image_path = "https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb";

    public void toFindQuestionParameters(FindQuestionParameters questionParameters, Activity a, ArrayList<Ad>l,User u){
        if (mResultsList!=null){
            mResultsList = null;
        }
        user = u;
        list = l;
        mResultsList = new ArrayList<>();
        activity = a;
        mQuestionParameters = questionParameters;
        /*GettingDataFromFirebase g = new GettingDataFromFirebase();
        g.getListAds(context);*/
        Log.d("list_size","list size start = " + list.size());
        takeListData();
    }

    public void takeListData(){
        for (Ad ad : list){
            equalsOrNot(ad);
        }
        Intent intent = new Intent(activity,FindResult.class);
        intent.putExtra("list",mResultsList);
        intent.putExtra("user",user);
        activity.startActivity(intent);
    }

    private void equalsOrNot(Ad ad) {
        if (mQuestionParameters.getTitle().equals("")||ad.getNameJobAd().contains(mQuestionParameters.getTitle())){

            if (mQuestionParameters.isPremium()&&ad.isPremium()||!mQuestionParameters.isPremium()&&!ad.isPremium()||!mQuestionParameters.isPremium()){

                if (ad.getCostAd()>mQuestionParameters.getPrice_from()){

                    if (ad.getCostAd()<mQuestionParameters.getPrice_to()){

                        if (mQuestionParameters.isOnly_photo()&&!ad.getImagePathAd_1().equals(image_path)||!mQuestionParameters.isOnly_photo()&&ad.getImagePathAd_1().equals(image_path)){

                            if (ad.getCategoty().contains(mQuestionParameters.getCategory())||mQuestionParameters.getCategory().equals("ВСЕ КАТЕГОРИИ")){

                                if (ad.getCity().contains(mQuestionParameters.getCity())||mQuestionParameters.getCity().equals("ВСЕ ГОРОДА")){

                                    mResultsList.add(ad);
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.d("list_size","list size = " + mResultsList.size());
        //BusStation.getBus().post(mResultsList);
        /*RecieveResultData _mRec = (RecieveResultData) activity;
        _mRec.someEvent(mResultsList);*/

    }
}
