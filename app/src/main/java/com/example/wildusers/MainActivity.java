package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Starting Screen of the Wild User application
 * Launching Screen of the application
 * User enter the UserID, condition, and rotation sequence
 */
public class MainActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/**
 * Navigating to next screen using Intents and post delays to automatically navigate to next screen after button press
 * */
        start = (Button) findViewById(R.id.getStartBtn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getApplicationContext(), StartUp.class);
                        startActivity(i);
                        finish();
                    }
                }, 10);
            }
        });

    }


}