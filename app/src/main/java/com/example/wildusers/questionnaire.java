package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.wildusers.Database.LocalDB.DBHelper;

public class questionnaire extends AppCompatActivity {

    DBHelper dbHelper;
    RatingBar rb_move, rb_walk, rb_busy, rb_tired;
    Button SubmitQuestionnaire;

    String UserID;
    float move, walk, busy, tired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        rb_move = (RatingBar) findViewById(R.id.ratingBar);
        rb_walk = (RatingBar) findViewById(R.id.ratingBar2);
        rb_busy = (RatingBar) findViewById(R.id.ratingBar3);
        rb_tired = (RatingBar) findViewById(R.id.ratingBar4);

        UserID = getIntent().getStringExtra("UserID");

        dbHelper = new DBHelper(this);

        SubmitQuestionnaire = (Button) findViewById(R.id.QsubmitBtn);
        SubmitQuestionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move = rb_move.getRating();
                walk = rb_walk.getRating();
                busy = rb_busy.getRating();
                tired = rb_tired.getRating();


                saveToDatabase(move, walk,busy,tired);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();

                        //destroy the app completely
                        System.exit(0);
                    }
                }, 1000*60*2);

            }
        });
    }


    public void saveToDatabase(float move,float walk, float busy, float tired){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToQuestionnaireTable(UserID, move, walk, busy,tired, database);
    }

}