package com.example.wildusers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class oneHourAlarmHandler {
    private Context context;

    public oneHourAlarmHandler(Context context) {
        this.context = context;
    }

    public void setAlarmManager(){
        Intent i = new Intent(context, alertScreen.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, i, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager != null){
            //triggering the service after 1 hour
            long triggerAfter = 60 * 1000;
            //repeat every hour after that
            long triggerEvery = 60 *  1000;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAfter, triggerEvery, sender);
        }
    }
}

