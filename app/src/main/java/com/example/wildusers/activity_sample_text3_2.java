package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_sample_text3_2 extends AppCompatActivity {

    ImageView next32;
    EditText text32;
    TextView phrase32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text3_2);

        text32 = (EditText) findViewById(R.id.typeText32);
        phrase32 = (TextView) findViewById(R.id.text32);

        next32 = (ImageView) findViewById(R.id.next32);
        next32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text3_2.this, activity_sample_text3_3.class);
                startActivity(i);
            }
        });
    }
}