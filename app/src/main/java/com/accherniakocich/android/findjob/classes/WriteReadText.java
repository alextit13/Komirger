package com.accherniakocich.android.findjob.classes;

import android.content.Context;
import android.util.Log;

import com.accherniakocich.android.findjob.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class WriteReadText {

    public static String readFromFile(Context context) {
        return new String(new StringBuilder(context.getResources().getString(R.string.user_permission)));
    }
}