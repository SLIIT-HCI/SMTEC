package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class activity_sample_text2_3 extends AppCompatActivity {

    ImageView next23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text2_3);

        next23 = (ImageView) findViewById(R.id.next23);
        next23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text2_3.this, activity_sample_text3_1.class);
                startActivity(i);
            }
        });
    }
}