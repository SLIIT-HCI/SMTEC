package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class alertScreen extends AppCompatActivity {

    RadioGroup alertGroup;
    RadioButton start, oneMin, twoMin, fiveMin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_screen);

        //making the activity appear every one hour
//        oneHourAlarmHandler alarmHandler = new oneHourAlarmHandler(this);
//        alarmHandler.setAlarmManager();



        alertGroup = (RadioGroup) findViewById(R.id.alertTime);
        start = (RadioButton) findViewById(R.id.startRB);
        twoMin = (RadioButton) findViewById(R.id.twoMinRB);
        fiveMin = (RadioButton) findViewById(R.id.fiveMinRB);
        
        /************************************************************************************************/
        alertGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                //calling Reminder function
                createNotificationChannel();

                if(i == R.id.startRB){
                    Intent i1 = new Intent(getApplicationContext(), activity_sample_text1_1.class);
                    startActivity(i1);
                    Toast.makeText(alertScreen.this, "Starting Experiment Now!", Toast.LENGTH_SHORT).show();
                }
                else if(i == R.id.oneMinRB) {
                    Intent i1 = new Intent(getApplicationContext(), ReminderBroadcast.class);
                    //startActivity(i1);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(alertScreen.this, 0, i1, 0);

                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    long timeAtButtonClick = System.currentTimeMillis();
                    long twoMinutes = 1000 * 60; //1 minute
                    alarmManager.set( AlarmManager.RTC_WAKEUP, timeAtButtonClick + twoMinutes, pendingIntent);
                    Toast.makeText(alertScreen.this, "Starting Experiment in 1 Minutes!", Toast.LENGTH_SHORT).show();
                }
                else if(i == R.id.twoMinRB) {
                    Intent i2 = new Intent(getApplicationContext(), ReminderBroadcast.class);
                    //startActivity(i1);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(alertScreen.this, 0, i2, 0);

                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    long timeAtButtonClick = System.currentTimeMillis();
                    long twoMinutes = 1000 * 120; //2 minutes

                    alarmManager.set( AlarmManager.RTC_WAKEUP, timeAtButtonClick + twoMinutes, pendingIntent);

                    Toast.makeText(alertScreen.this, "Starting Experiment in 2 Minutes!", Toast.LENGTH_SHORT).show();

                }

                else if(i == R.id.fiveMinRB) {
                    Intent i3 = new Intent(getApplicationContext(), ReminderBroadcast.class);
//                    startActivity(i3);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(alertScreen.this, 0, i3, 0);

                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    long timeAtButtonClick = System.currentTimeMillis();
                    long twoMinutes = 1000 * 300; //5 minutes
                    alarmManager.set( AlarmManager.RTC_WAKEUP, timeAtButtonClick + twoMinutes, pendingIntent);
                    Toast.makeText(alertScreen.this, "Starting Experiment in 5 Minutes!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    /**********************************************************************************************************/

    //code implementation for the reminder
  //  @RequiresApi(api = Build.VERSION_CODES.O)

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "AlertReminderChannel";
            String description = "Channel for Experiment Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyToStart",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
   }


}