package com.example.smtec;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BreakTimeActivity extends AppCompatActivity {

    TextView brk_timer;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breaktime);

        brk_timer = findViewById(R.id.break_timer);

        new CountDownTimer(60000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                brk_timer.setText("BreakTime remaining: " + formatTime(millisUntilFinished));
            }
            public void onFinish()
            {
                brk_timer.setText("Let's start again !");
            }
        }.start();
    }
}
