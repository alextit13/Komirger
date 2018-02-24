package com.accherniakocich.android.findjob.find;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.QuestionFind;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.find.converter.JSON_Converter;
import com.accherniakocich.android.findjob.findResult.FindResults;
import com.accherniakocich.android.findjob.interfaces.TestInterface;

import java.util.ArrayList;

public class FindLogic implements TestInterface{
    private ArrayList<Ad>listAds,sorting_list;
    private QuestionFind questionFind;
    private Activity activity;
    private User user;
    private String image_path_default = "https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb";

    private void sortingListAds(){
        sorting_list = new ArrayList<>();

        for (int i = 0; i<listAds.size();i++){

            Ad ad = listAds.get(i);

            if (ad.getNameJobAd().equals(questionFind.getTitle())||questionFind.getTitle().equals("")){

                if (ad.isPremium()&&questionFind.getPremium()==1||!ad.isPremium()&&questionFind.getPremium()==2||questionFind.getPremium()==0){

                    if (ad.getCostAd()>questionFind.getCost_from()&&ad.getCostAd()<questionFind.getCost_to()){

                        if (!questionFind.isOnly_photo() || !ad.getImagePathAd_1().equals(image_path_default)){

                            if (ad.getCategoty().equals(questionFind.getCategory())||questionFind.getCategory().equals("ВСЕ КАТЕГОРИИ")){

                                if (ad.getCity().equals(questionFind.getCity())||questionFind.getCity().equals("ВСЕ ГОРОДА")){

                                    sorting_list.add(ad);
                                }
                            }
                        }
                    }
                }
            }
        }
        toFindResults();
    }

    private void toFindResults() {
        if (sorting_list!=null&&sorting_list.size()!=0){
            Intent intent = new Intent(activity, FindResults.class);
            JSON_Converter json_converter = new JSON_Converter();
            intent.putExtra("user",user);
            intent.putExtra("list",json_converter.convertToStringArray(sorting_list));
            activity.startActivity(intent);
        }else{
            Toast.makeText(activity, "К сожалению, ничего не найдено. \n Введите другие параметры поиска", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void someEvent(Activity a, ArrayList<Ad> list, QuestionFind question, User u) {
        user = u;
        listAds = list;
        questionFind = question;
        activity = a;
        sortingListAds();
    }
}