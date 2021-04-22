package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class rating extends AppCompatActivity {

    RatingBar rb5, rb6, rb7;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        rb5 = (RatingBar) findViewById(R.id.ratingBar5);
        rb6 = (RatingBar) findViewById(R.id.ratingBar6);
        rb7 = (RatingBar) findViewById(R.id.ratingBar7);

        submit = (Button)findViewById(R.id.RSubmitTN);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), rating2.class);
                startActivity(i);
            }
        });
    }
}