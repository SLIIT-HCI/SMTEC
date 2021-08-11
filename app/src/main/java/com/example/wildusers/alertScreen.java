package com.example.wildusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

public class alertScreen extends AppCompatActivity {

    RadioGroup alertGroup;
    RadioButton start, oneMin, twoMin, fiveMin;
    String UserID;
    int session;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_screen);

        pref = getApplicationContext().getSharedPreferences("Pref", 0);
        editor = pref.edit();

        setupAlarm();
        UserID = pref.getString("UserID","");
        session = getIntent().getIntExtra("session",0);


        /***************  Waking up alert screen even when the app is closed / screen is locked / screen is offed *************/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);

            System.out.println("test 1");
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            System.out.println("Appears even phone is locked");
        }


        alertGroup = (RadioGroup) findViewById(R.id.alertTime);
        start = (RadioButton) findViewById(R.id.startRB);
        oneMin = (RadioButton) findViewById(R.id.oneMinRB);
        twoMin = (RadioButton) findViewById(R.id.twoMinRB);
        fiveMin = (RadioButton) findViewById(R.id.fiveMinRB);


/************************************************ implementing the option radio buttons ************************************************/


       alertGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {

               //calling Reminder function
               createNotificationChannel();

               if (i == R.id.startRB) {
                   Intent i1 = new Intent(getApplicationContext(), activity_sample_text1_3.class);
                   i1.putExtra("UserID", UserID);
                   i1.putExtra("session", session);
                   startActivity(i1);
                   Toast.makeText(alertScreen.this, "Starting Experiment Now!", Toast.LENGTH_SHORT).show();
               } else if (i == R.id.oneMinRB) {
                   Intent i1 = new Intent(getApplicationContext(), ReminderBroadcast.class);
                   i1.putExtra("UserID", UserID);
                   i1.putExtra("session", session);

                   Toast.makeText(alertScreen.this, "Starting Experiment in 1 Minutes!", Toast.LENGTH_SHORT).show();

                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {

                           Intent intent = new Intent(alertScreen.this, alertScreen.class);
                           intent.putExtra("UserID", UserID);
                           intent.putExtra("session", session);
                           startActivity(intent);
                           Toast.makeText(alertScreen.this, "Starting Experiment!", Toast.LENGTH_SHORT).show();
                       }
                   }, 1000 * 10);

                   moveTaskToBack(true);

               } else if (i == R.id.twoMinRB) {
                   Intent i2 = new Intent(getApplicationContext(), ReminderBroadcast.class);
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {

                           Intent intent = new Intent(alertScreen.this, alertScreen.class);
                           intent.putExtra("UserID", UserID);
                           intent.putExtra("session", session);
                           startActivity(intent);
                           Toast.makeText(alertScreen.this, "Starting Experiment!", Toast.LENGTH_SHORT).show();
                       }
                   }, 1000 * 60);

                   //move the activity to background
                   moveTaskToBack(true);
               } else if (i == R.id.fiveMinRB) {
                   Intent i3 = new Intent(getApplicationContext(), ReminderBroadcast.class);

                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {

                           Intent intent = new Intent(alertScreen.this, alertScreen.class);
                           intent.putExtra("UserID", UserID);
                           intent.putExtra("session", session);
                           startActivity(intent);
                           Toast.makeText(alertScreen.this, "Starting Experiment!", Toast.LENGTH_SHORT).show();
                       }
                   }, 1000 * 60);

                   //move the activity to background
                   moveTaskToBack(true);
               }
           }
       });
    }

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

/******************************* making this alert screen appear even when the app is closed by using alarm manager *********************/
    private void setupAlarm() {

        // Intent to start the Broadcast Receiver
        Intent ia = new Intent(this, AlarmReceiver.class);

        // PendingIntent: this is the pending intent, which waits until the right time, to be called by AlarmManager
        // The Pending Intent to pass in AlarmManager
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, ia, 0);

        AlarmManager am = (AlarmManager)   this.getSystemService(Context.ALARM_SERVICE);
        //am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 1000*60*3, pendingIntent);

    }



    /******************************** mini menu **********************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mini_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.activeTime:
                Intent i1 = new Intent(getApplicationContext(), ActiveTime_Selection.class);
                i1.putExtra("UserID", UserID);
                startActivity(i1);
                return true;

            case R.id.Instructions:
                Intent i2 = new Intent(getApplicationContext(), Instructions.class);
                startActivity(i2);
                return true;

            case R.id.settings:
                Intent i3 = new Intent(getApplicationContext(), settings.class);
                startActivity(i3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /******************************************* end of mini-menu ***********************************************************/


}