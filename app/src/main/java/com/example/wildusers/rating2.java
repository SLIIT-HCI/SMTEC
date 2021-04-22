package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class rating2 extends AppCompatActivity {

    Button rateSubmit;
    RadioGroup type, postureRG;
    RadioButton stk, sgk, singleThumb, singleFinger, doubleThumb;
    EditText OpenComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating2);

        type = (RadioGroup) findViewById(R.id.type);
        postureRG = (RadioGroup) findViewById(R.id.postureRG);
        stk = (RadioButton) findViewById(R.id.STK);
        sgk = (RadioButton) findViewById(R.id.SGK);
        singleThumb = (RadioButton) findViewById(R.id.singleThumb);
        singleFinger = (RadioButton) findViewById(R.id.singleFinger);
        doubleThumb = (RadioButton) findViewById(R.id.doubleThumb);
        OpenComment = (EditText) findViewById(R.id.commentEdit);


//        rateSubmit = (Button)findViewById(R.id.rate2SubmitBTN);

//        rateSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), questionnaire.class);
//                startActivity(i);
//            }
//        });
    }
}