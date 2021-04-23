package com.example.labexperiment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class BreakTimeActivity extends AppCompatActivity {

    TextView brk_timer;
    long timeleft;
    int nextRun = 1;
    RatingBar ratingSpeed, ratingAccuracy, ratingEaseOfUse, ratingPreference;
    float speed, accuracy, easeOfUse, preference;
    String email;
    int session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaktime);

        brk_timer = (TextView) findViewById(R.id.break_timer);
        ratingSpeed = (RatingBar) findViewById(R.id.ratingBarSpeed);
        ratingAccuracy = (RatingBar) findViewById(R.id.ratingBarAccuracy);
        ratingPreference = (RatingBar) findViewById(R.id.ratingBarPreference);
        ratingEaseOfUse = (RatingBar) findViewById(R.id.ratingBarEaseOfUse);

        email = getIntent().getStringExtra("email");
        session = getIntent().getIntExtra("session",0);

            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    brk_timer.setText("BreakTime remaining: " + formatTime(millisUntilFinished));
                    timeleft = millisUntilFinished / 1000;
                }

                public void onFinish() {
                    brk_timer.setText("Let's start again !");
                    speed = ratingSpeed.getRating();
                    accuracy = ratingAccuracy.getRating();
                    preference = ratingPreference.getRating();
                    easeOfUse = ratingEaseOfUse.getRating();
                    saveToDatabase(email,session,speed,accuracy,preference,easeOfUse);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(BreakTimeActivity.this, sessionPeriod.class);
                    startActivity(intent);
                }
            }.start();
    }
    public void saveToDatabase(String email,int session,float speed,float accuracy,float preference,float easeOfUse){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToRatingsTable(email,session,speed,accuracy,preference,easeOfUse,database);
    }

    public String formatTime(long millis) {
        String output = "00:00";
        long seconds = millis / 1000;
        long minutes = seconds / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        String sec = String.valueOf(seconds);
        String min = String.valueOf(minutes);

        if (seconds < 10)
            sec = "0" + seconds;
        if (minutes < 10)
            min= "0" + minutes;

        output = min + " : " + sec;
        return output;
    }
}
