package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_sample_text1_2 extends AppCompatActivity {

    ImageView next12;
    EditText text12;
    TextView phrase2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text1_2);

        text12 = (EditText) findViewById(R.id.typeText12);
        phrase2 = (TextView) findViewById(R.id.text12);

        next12 = (ImageView) findViewById(R.id.next12);
        next12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text1_2.this, activity_sample_text1_3.class);
                startActivity(i);
            }
        });
    }
}