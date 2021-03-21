package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class activity_sample_text1_3 extends AppCompatActivity {

    ImageView next13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text1_3);

        next13 = (ImageView) findViewById(R.id.next13);
        next13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text1_3.this, activity_sample_text2_1.class);
                startActivity(i);
            }
        });
    }
}