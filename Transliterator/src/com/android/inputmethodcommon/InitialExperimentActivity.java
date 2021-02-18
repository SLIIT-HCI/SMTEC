package com.example.smtec;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.nfc.cardemulation.CardEmulation;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class InitialExperimentActivity extends AppCompatActivity {

    TextView timer,phrase;
    Button btn_next ,btn_start,btn_cancel;
    EditText input_phrase;
    Boolean clicked = false;
    Integer clickCount = 0;
    int noOfErrors = 0;
    long timeleft;
    String [] phrase_array = new String[5];
    String [] save_phrase_array = new String[phrase_array.length];
    char [] charctersTyped_array = new char[phrase_array.length];

    double startTime,endTime;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_experiment);

        timer = findViewById(R.id.timer_view);
        phrase = findViewById(R.id.id_sentence);
        btn_next = findViewById(R.id.btn_next);
        input_phrase = findViewById(R.id.text_enter);
        btn_start = findViewById(R.id.btn_start);
        btn_cancel = findViewById(R.id.btn_cancel);

        final CountDownTimer countDown = new CountDownTimer(60000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                if(clicked == false) {
                    setPhrase(0);
                }
                timer.setText("Time remaining: " + formatTime(millisUntilFinished));
                timeleft = millisUntilFinished/1000;
            }
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onFinish()
            {
                endTime = LocalTime.now().toNanoOfDay();
                timer.setText("Your Time is over !");
                phrase.setText("");
                input_phrase.setText("");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(InitialExperimentActivity.this, ExperimentSummary.class);
                startActivity(intent);
            }
        };

        btn_start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                countDown.start();
                startTime = LocalTime.now().toNanoOfDay();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                endTime = LocalTime.now().toNanoOfDay();
                countDown.cancel();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
               clicked = true;
               phraseArray_Iterator();
               calculateError();
            }
        });
        input_phrase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    while(btn_next.isPressed() && timeleft!=0){
                        saveInputPhrase();
                    }
                }
            }
        });
        calculateWordsPerMinute();
    }

    public void setPhrase(Integer count){

        phrase_array[0] = "You are ready to learn and do your best, but you are also nervous.";
        phrase_array[1] = "Sometimes the most difficult questions have the simplest solutions";
        phrase_array[2] = "Congratulations on your new job";
        phrase_array[3] = "Starting a new job is exciting but stressful.";
        phrase_array[4] = "Tomorrow is second Saturday.";

        phrase.setText(phrase_array[count]);
    }

    public void phraseArray_Iterator(){

        clickCount = clickCount + 1;
        if(clickCount > 4){
            clickCount = 0;
        }
        setPhrase(clickCount);
        input_phrase.setText("");
    }
    public void saveInputPhrase(){

        //Timestamp ts = new Timestamp(System.currentTimeMillis());
        String inputText = input_phrase.getText().toString();
        for(int i=0;i<phrase_array.length;i++){
            save_phrase_array[i] = inputText;
            charctersTyped_array[i] = save_phrase_array[i].charAt(i);
        }
    }

    public int calculateError(){

        String phrase1, phrase2;

        phrase1 = input_phrase.getText().toString();
        phrase2 = phrase.getText().toString();
        noOfErrors += levenshteinDistance(phrase1,phrase2);

        return noOfErrors;
    }

    public int calculateWordsPerMinute(){

        // x (characters / 5) / 1min = y wpm
        int wordCount = 0;
        int wpm;
        double duration = endTime - startTime;
        double seconds = duration/1000000000.0;

        for(int i=0;i<charctersTyped_array.length;i++){
            wordCount += 1;
        }
        wpm = (int)((((double)wordCount / 5)/ seconds)* 60 );
        return wpm;
    }

    // calculating edit distance
    public static int levenshteinDistance( String s1, String s2 ) {
        return dist( s1.toCharArray(), s2.toCharArray() );
    }

    public static int dist( char[] s1, char[] s2 ) {

        int[][] d = new int[ s1.length + 1 ][ s2.length + 1 ];
        for( int i = 0; i < s1.length + 1; i++ ) {
            d[ i ][ 0 ] = i;
        }
        for(int j = 0; j < s2.length + 1; j++) {
            d[ 0 ][ j ] = j;
        }
        for( int i = 1; i < s1.length + 1; i++ ) {
            for( int j = 1; j < s2.length + 1; j++ ) {
                int d1 = d[ i - 1 ][ j ] + 1;
                int d2 = d[ i ][ j - 1 ] + 1;
                int d3 = d[ i - 1 ][ j - 1 ];
                if ( s1[ i - 1 ] != s2[ j - 1 ] ) {
                    d3 += 1;
                }
                d[ i ][ j ] = Math.min( Math.min( d1, d2 ), d3 );
            }
        }
        return d[ s1.length ][ s2.length ];
    }
}