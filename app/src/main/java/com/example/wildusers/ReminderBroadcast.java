package com.example.wildusers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    String UserID;
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
        //intent.putExtra("msg", str);
        context.startActivity(intent);




//
//        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//                    Intent i = new Intent(context, activity_sample_text1_3.class);
//                    PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                            SystemClock.elapsedRealtime() + 1000*60,
//                            1000*60, alarmIntent);

    }
}
