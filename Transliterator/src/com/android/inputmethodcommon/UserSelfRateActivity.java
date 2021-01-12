package com.example.smtec;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserSelfRateActivity extends AppCompatActivity {

    RatingBar rtb;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selfrate);
        rtb = findViewById(R.id.user_rating);
     //   btn = findViewById(R.id.btn_submit);

        rtb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            }
        });

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = String.valueOf(rtb.getRating());
                Toast.makeText(getApplicationContext(),s+"star",Toast.LENGTH_SHORT).show();
            }
        });  */

    }
}
