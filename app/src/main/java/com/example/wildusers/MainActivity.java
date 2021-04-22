package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.getStartBtn);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StartUp.class);
                startActivity(i);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Intent i = new Intent(getApplicationContext(), StartUp.class);
                        //startActivity(i);
                        finish();
                    }
                }, 900);
            }
        });


    }
}