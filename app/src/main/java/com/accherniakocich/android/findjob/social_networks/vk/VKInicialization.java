package com.accherniakocich.android.findjob.social_networks.vk;

import android.app.Application;

import com.vk.sdk.VKSdk;

public class VKInicialization extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
