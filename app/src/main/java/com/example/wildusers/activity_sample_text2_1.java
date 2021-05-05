package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_sample_text2_1 extends AppCompatActivity {

    ImageView next21;
    EditText text21;
    TextView phrase21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text2_1);

        text21 = (EditText) findViewById(R.id.typeText21);
        phrase21 = (TextView) findViewById(R.id.text21);

        next21 = (ImageView) findViewById(R.id.next21);
        next21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text2_1.this, activity_sample_text2_2.class);
                startActivity(i);
            }
        });
    }
}