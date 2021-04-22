package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_sample_text3_1 extends AppCompatActivity {

    ImageView next31;
    EditText text31;
    TextView phrase31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text3_1);

        text31 = (EditText) findViewById(R.id.typeText31);
        phrase31 = (TextView) findViewById(R.id.text31);

        next31 = (ImageView) findViewById(R.id.next31);
        next31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text3_1.this, activity_sample_text3_2.class);
                startActivity(i);
            }
        });
    }
}