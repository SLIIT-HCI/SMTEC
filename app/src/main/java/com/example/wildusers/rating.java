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
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.S");

    String UserID;
    String comment;

    float speed, accuracy, easeOfUse;
    String preference, HandPosture;
    int totalRuns;
    int noOfRuns = 1;
    long timeleft;
    String formatDateTime;
    LocalDateTime sessionOnFinished;
    String session;

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
        session = getIntent().getStringExtra("session");

        System.out.println("total runs" + totalRuns);
        if(totalRuns != 0){
            noOfRuns = totalRuns;
        }


        dbHelper = new DBHelper(this);
        /********************************/
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                brk_timer.setText("         Submit Your Rating: " + formatTime(millisUntilFinished));
                timeleft = millisUntilFinished / 1000;
            }

            public void onFinish() {
                brk_timer.setText("       Let's start again !");


//                int selectedIDPreference = type.getCheckedRadioButtonId();
//                Type_radioBtn = (RadioButton) findViewById(selectedIDPreference);
//                preference = (String) Type_radioBtn.getText().toString();
//
//                int selectedIDHandPosture = postureRG.getCheckedRadioButtonId();
//                TypeHandPosture = (RadioButton)findViewById(selectedIDHandPosture);
//                HandPosture = (String) TypeHandPosture.getText().toString();
//
//                speed = rb5_speed.getRating();
//                accuracy = rb6_accuracy.getRating();
//                easeOfUse = rb7_easeOfUse.getRating();
//                //String comment = OpenComment.getText().toString();
//                comment = OpenComment.getText().toString();


//                sessionOnFinished = LocalDateTime.now();
                formatDateTime = sessionOnFinished.format(formatDate);
               // saveToDatabase(email,session,noOfRuns,speed,accuracy,preference,easeOfUse, formatDateTime);
                //saveToDatabase(speed,accuracy,preference,easeOfUse,HandPosture, comment);

                System.out.println("Before" + noOfRuns);
                noOfRuns = noOfRuns +1;
                if(noOfRuns<2){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(rating.this, activity_sample_text1_3.class);
                            intent.putExtra("noOfRuns", noOfRuns);
                            startActivity(intent);
                        }
                    }, 3000);

                }else{
                    brk_timer.setText("            Session is over");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent=new Intent(rating.this, activity_sample_text2_3.class);
                            intent.putExtra("noOfRuns", noOfRuns);
                            intent.putExtra("session", session);
                            startActivity(intent);
                        }
                    }, 3000);
                }
                System.out.println("After" + noOfRuns);

            }
        }.start();

        /*******************************/


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

            }
        });
    }

    public void saveToDatabase(String session, float speed,float accuracy, String preference,float easeOfUse, String HandPosture, String comment){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToRatingsTable(UserID, session, noOfRuns, speed,accuracy,preference,easeOfUse, HandPosture, comment, database);
    }


    public String formatTime(long millis) {
        String output = "00:00";
        long seconds = millis / 1000;
        long minutes = seconds / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        String sec = String.valueOf(seconds);
        String min = String.valueOf(minutes);

        if (seconds < 10)
            sec = "0" + seconds;
        if (minutes < 10)
            min= "0" + minutes;

        output = min + " : " + sec;
        return output;
    }
}