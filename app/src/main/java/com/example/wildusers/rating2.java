package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wildusers.Database.DBHelperRating;

public class rating2 extends AppCompatActivity {

    Button rateSubmit;
    RadioGroup type, postureRG;
    RadioButton stk, sgk, singleThumb, singleFinger, doubleThumb;
    EditText OpenComment, test;

    DBHelperRating dbHandler;


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
        test = (EditText) findViewById(R.id.test);


//        rateSubmit = (Button)findViewById(R.id.rate2SubmitBTN);

//        rateSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), questionnaire.class);
//                startActivity(i);
//            }
//        });

         dbHandler = new DBHelperRating(this);
        

    }

    public void inserRatingBtn(View view){
        int id = Integer.parseInt(test.getText().toString());
        String cmt = OpenComment.getText().toString();

        boolean status = dbHandler.addRatingData(id, cmt);

        if(status)
            Toast.makeText(this, "Your Rating is Saved Successfully!!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Sorry! Your Rating is not Saved!!", Toast.LENGTH_SHORT).show();

    }
}