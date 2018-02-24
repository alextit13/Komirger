package com.accherniakocich.android.findjob.interfaces;

import android.app.Activity;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.classes.QuestionFind;
import com.accherniakocich.android.findjob.classes.User;

import java.util.ArrayList;

public interface TestInterface {
    public void someEvent(Activity activity, ArrayList<Ad>list, QuestionFind question, User user);
};