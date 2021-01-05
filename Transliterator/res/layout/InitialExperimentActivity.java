package com.example.smtec;

import android.nfc.cardemulation.CardEmulation;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class InitialExperimentActivity extends AppCompatActivity {

    TextView timer,phrase;
    Button btn_next;
    EditText input_phrase;
    Boolean clicked = false;
    Integer clickCount = 0;

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

     public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_initial_experiment);

       timer = findViewById(R.id.timer_view);
       phrase = findViewById(R.id.id_sentence);
       btn_next = findViewById(R.id.next_view);
       input_phrase = findViewById(R.id.text_enter);

         btn_next.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 clicked = true;
                 savePhrase();
             }
         });

       new CountDownTimer(60000, 1000)
       {
           public void onTick(long millisUntilFinished)
           {
               if(clicked == false) {
                   setPhrase(0);
               }
               timer.setText("Time remaining: " + formatTime(millisUntilFinished));
           }
           public void onFinish()
           {
               timer.setText("Your Time is over !");
               phrase.setText("");
           }
       }.start();
     }
     public void setPhrase(Integer count){
         String phrase_array[] = new String[5];

         phrase_array[0] = "You are ready to learn and do your best, but you are also nervous.";
         phrase_array[1] = "Sometimes the most difficult questions have the simplest solutions";
         phrase_array[2] = "Congratulations on your new job";
         phrase_array[3] = "Starting a new job is exciting but stressful.";
         phrase_array[4] = "Tomorrow is second Saturday.";

         phrase.setText(phrase_array[count]);
     }
     public void savePhrase(){

         clickCount = clickCount + 1;
         if(clickCount > 4){
             clickCount = 0;
         }
         setPhrase(clickCount);
         input_phrase.setText("");
     }
}
