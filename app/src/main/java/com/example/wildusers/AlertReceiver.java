package com.example.wildusers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    private SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("________________________Alert Received___________________________________");

        pref = context.getSharedPreferences("MyPref", 0);

        Intent i = new Intent(context, alertScreen.class);
        //i.putExtra("UserID", pref.getString("UserID",""));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}