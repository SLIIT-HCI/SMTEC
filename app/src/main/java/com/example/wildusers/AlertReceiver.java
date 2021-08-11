package com.example.wildusers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AlertReceiver extends BroadcastReceiver {
    private SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("________________________Alert Received___________________________________");

        pref = context.getSharedPreferences("Pref", 0);

        Intent i = new Intent(context, alertScreen.class);
        i.putExtra("UserID", pref.getString("UserID",""));
        context.startActivity(i);

    }
}