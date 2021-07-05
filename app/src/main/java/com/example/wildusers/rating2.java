package com.example.wildusers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wildusers.Database.LocalDB.DBHandlerRating;

public class rating2 extends AppCompatActivity {

    Button rateSubmit;
    RadioGroup type, postureRG;
    RadioButton stk, sgk, singleThumb, singleFinger, doubleThumb;
    EditText OpenComment, test;

    DBHandlerRating DB;


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
        OpenComment = findViewById(R.id.commentEdit);
        test = (EditText) findViewById(R.id.test);


        rateSubmit = findViewById(R.id.rate2SubmitBTN);

        DB = new DBHandlerRating(this);
        rateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = OpenComment.getText().toString();

                Boolean chkInsert = DB.insertData(name);
                if (chkInsert == true){
                    Toast.makeText(rating2.this,"Inserted", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(rating2.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });

    }


}