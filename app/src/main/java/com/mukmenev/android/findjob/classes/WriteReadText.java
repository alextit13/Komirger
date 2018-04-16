package com.mukmenev.android.findjob.classes;

import android.content.Context;

import com.mukmenev.android.findjob.R;

public class WriteReadText {

    public static String readFromFile(Context context) {
        return new String(new StringBuilder(context.getResources().getString(R.string.user_permission)));
    }
}