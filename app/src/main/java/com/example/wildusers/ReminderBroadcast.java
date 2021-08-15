package com.example.wildusers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyToStart")
                .setSmallIcon(R.drawable.notifications_icon)
                .setContentTitle("Reminder")
                .setContentText("Your Experiment will be starting!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());

        /*******************************************************************************************************/
        //calling the next activity that should appear after the specific time
        Toast.makeText(context, "Starting Your Experiment!!", Toast.LENGTH_LONG).show();
        intent = new Intent();
        intent.setClass(context, alertScreen.class); //class name where to redirect
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
