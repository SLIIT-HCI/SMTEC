package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.wildusers.Database.LocalDB.DBHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class rating extends AppCompatActivity {

    DBHelper dbHelper;
    RatingBar rb5_speed, rb6_accuracy, rb7_easeOfUse;
    RadioGroup type, postureRG;
    RadioButton Type_radioBtn, TypeHandPosture;
    EditText OpenComment;
    TextView brk_timer;

    String UserID;
    String comment;
    float speed, accuracy, easeOfUse;
    String preference, HandPosture;
    int totalRuns;
    int noOfRuns = 1;
    int session;

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
        OpenComment = findViewById(R.id.commentEdit);
        submit = (Button)findViewById(R.id.rate2SubmitBTN);
        brk_timer = (TextView) findViewById(R.id.break_timer);

        UserID = getIntent().getStringExtra("UserID");
        totalRuns = getIntent().getIntExtra("noOfRuns",0);
        session = getIntent().getIntExtra("session",0);

        System.out.println("total runs" + totalRuns);
        if(totalRuns != 0){
            noOfRuns = totalRuns;
        }


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
                //String comment = OpenComment.getText().toString();
                comment = OpenComment.getText().toString();


                saveToDatabase(session, speed,accuracy,preference,easeOfUse,HandPosture, comment);



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent=new Intent(rating.this, questionnaire.class);
                        intent.putExtra("UserID", UserID);
                        startActivity(intent);
                    }
                }, 1000*60);

            }
        });
    }

    public void saveToDatabase(int session, float speed,float accuracy, String preference,float easeOfUse, String HandPosture, String comment){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToRatingsTable(UserID, session, speed,accuracy,preference,easeOfUse, HandPosture, comment, database);
    }

}