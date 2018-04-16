package com.accherniakocich.android.findjob.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastServiceRestart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context.getApplicationContext(),ServiceNotificationUsers.class));
    }
}
