package com.example.wildusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartUp extends AppCompatActivity {

    private Handler mHandler = new Handler();

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


        //calling alert screen every one hour
        //activityRunnable.run();

        //Navigating to the text entry interface
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), activity_sample_text1_3.class);
                startActivity(i);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Intent i = new Intent(getApplicationContext(), activity_sample_text1_3.class);
//                        startActivity(i);
                        finish();
                        //Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                    }
                }, 1000*60);
            }
        });

    }


    /******************************************* repeating activity every one hour *********************************************/
//    private Runnable activityRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(StartUp.this, "Repeating Activity Every One Hour", Toast.LENGTH_SHORT).show();
//            mHandler.postDelayed(this, 1000*60);
//            Intent i = new Intent(getApplicationContext(), alertScreen.class);
//            startActivity(i);
////            activityRunnable.run();
//        }
//    };


    /******************************** mini menu **********************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mini_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.activeTime:
                Intent i1 = new Intent(getApplicationContext(), activeTime.class);
                startActivity(i1);
                return true;

            case R.id.Instructions:
                Intent i2 = new Intent(getApplicationContext(), Instructions.class);
                startActivity(i2);
                return true;

            case R.id.settings:
                Intent i3 = new Intent(getApplicationContext(), settings.class);
                startActivity(i3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /******************************************* end of mini-menu ***********************************************************/

}