package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

public class alertScreen extends AppCompatActivity {

    RadioButton start, oneMin, twoMin, fiveMin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_screen);

        start = (RadioButton) findViewById(R.id.startRB);
        oneMin = (RadioButton) findViewById(R.id.oneMinRB);
        twoMin = (RadioButton) findViewById(R.id.twoMinRB);
        fiveMin = (RadioButton) findViewById(R.id.fiveMinRB);

        //To appear once a hour


    }
}