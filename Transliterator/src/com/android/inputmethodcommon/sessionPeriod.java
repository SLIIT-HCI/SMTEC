package com.example.labexperiment;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sessionPeriod extends AppCompatActivity {

    RadioGroup radioGroupFeedback;
    RadioButton radioButton;
    Button btn_submit;
    String handPosture,userComment;
    EditText comment;
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.activity_session_period);
        super.onCreate(savedInstanceState);

        radioGroupFeedback = (RadioGroup) findViewById(R.id.radioInputMode);
        comment = (EditText) findViewById(R.id.comment);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               int selectedId = radioGroupFeedback.getCheckedRadioButtonId();
               radioButton = (RadioButton) findViewById(selectedId);
               handPosture = (String) radioButton.getText().toString();
               userComment = comment.getText().toString();
               saveComment(handPosture,userComment);
            }
        });
    }
    public void saveComment(String handPosture, String comment){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToCommentTable(handPosture,comment,database);
    }
}
