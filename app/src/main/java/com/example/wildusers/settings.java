package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class settings extends AppCompatActivity {

    Button downloadGBoard;
    FloatingActionButton settingsDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        downloadGBoard = findViewById(R.id.downloadGBoardBtn);
        downloadGBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), webViewPlayStore.class);
                startActivity(i);
            }
        });

        settingsDone = findViewById(R.id.settingsOkBTN);
        settingsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StartUp.class);
                startActivity(i);
            }
        });
    }
}