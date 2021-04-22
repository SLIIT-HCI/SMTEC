package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_sample_text2_2 extends AppCompatActivity {

    ImageView next22;
    EditText text22;
    TextView phrase22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text2_2);

        text22 = (EditText) findViewById(R.id.typeText22);
        phrase22 = (TextView) findViewById(R.id.text22);

        next22 = (ImageView) findViewById(R.id.next22);
        next22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text2_2.this, activity_sample_text2_3.class);
                startActivity(i);
            }
        });
    }
}