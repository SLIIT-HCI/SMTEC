package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.example.wildusers.Database.LocalDB.DBHelper;

public class rating extends AppCompatActivity {

    DBHelper dbHelper;
    RatingBar rb5_speed, rb6_accuracy, rb7_easeOfUse;
    //Button rateSubmit;
    RadioGroup type, postureRG;
    //RadioButton stk, sgk, singleThumb, singleFinger, doubleThumb;
    RadioButton Type_radioBtn, TypeHandPosture;
    EditText OpenComment;

    String PassUserID;

    float speed, accuracy, easeOfUse;
    String preference, HandPosture;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        rb5_speed = (RatingBar) findViewById(R.id.ratingBar5);
        rb6_accuracy = (RatingBar) findViewById(R.id.ratingBar6);
        rb7_easeOfUse = (RatingBar) findViewById(R.id.ratingBar7);

        type = (RadioGroup) findViewById(R.id.type);
        postureRG = (RadioGroup) findViewById(R.id.postureRG);

//        stk = (RadioButton) findViewById(R.id.STK);
//        sgk = (RadioButton) findViewById(R.id.SGK);
//        singleThumb = (RadioButton) findViewById(R.id.singleThumb);
//        singleFinger = (RadioButton) findViewById(R.id.singleFinger);
//        doubleThumb = (RadioButton) findViewById(R.id.doubleThumb);
        OpenComment = findViewById(R.id.commentEdit);
        submit = (Button)findViewById(R.id.rate2SubmitBTN);

        PassUserID = getIntent().getStringExtra("PassUserID");

        dbHelper = new DBHelper(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedIDPreference = type.getCheckedRadioButtonId();
                Type_radioBtn = (RadioButton) findViewById(selectedIDPreference);
                preference = (String) Type_radioBtn.getText().toString();

                int selectedIDHandPosture = postureRG.getCheckedRadioButtonId();
                TypeHandPosture = (RadioButton)findViewById(selectedIDHandPosture);
                HandPosture = (String) TypeHandPosture.getText().toString();

                speed = rb5_speed.getRating();
                accuracy = rb6_accuracy.getRating();
                easeOfUse = rb7_easeOfUse.getRating();
                String comment = OpenComment.getText().toString();


                saveToDatabase(speed,accuracy,preference,easeOfUse,HandPosture, comment);

            }
        });
    }

    public void saveToDatabase(float speed,float accuracy, String preference,float easeOfUse, String HandPosture, String comment){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToRatingsTable(speed,accuracy,preference,easeOfUse, HandPosture, comment, database);
    }
}