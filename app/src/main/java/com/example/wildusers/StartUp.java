package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartUp extends AppCompatActivity {

    Button start;
    EditText ID, condition, rotationSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);


        start = (Button)findViewById(R.id.startBtn);
        ID = (EditText)findViewById(R.id.uIDET);
        condition = (EditText)findViewById(R.id.ConditionET);
        rotationSequence = (EditText)findViewById(R.id.rotationSequenceET);


        //Navigating to the text entry interface
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), activity_sample_text1_1.class);
                startActivity(i);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Intent i = new Intent(getApplicationContext(), activity_sample_text1_1.class);
//                        startActivity(i);
                        finish();
                        //Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                    }
                }, 900);
            }
        });

    }
}