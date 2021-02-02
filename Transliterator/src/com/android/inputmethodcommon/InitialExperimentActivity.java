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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class InitialExperimentActivity extends AppCompatActivity {

    TextView timer,phrase;
    Button btn_next;
    EditText input_phrase;
    Boolean clicked = false;
    Integer clickCount = 0;
    Integer count1 = 0;
    Integer count2 ,totalCha ;
    long timeleft;
    String phrase_array[] = new String[5];
    String text_input_array[] = new String[5];

    int characterTyped = 0;
    String text_input,index;
    char phrase_displyed_array[] = new char[phrase_array.length];
    char char_input_array[] = new char[text_input_array.length];
    char typedCharacter;
    int errors;

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
                timeleft = millisUntilFinished/1000;
            }
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onFinish()
            {
                timer.setText("Your Time is over !");
                phrase.setText("");
            }
        }.start();
        /*input_phrase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    double startTime = LocalTime.now().toNanoOfDay();
                    while(btn_next.isPressed()){
                        store_array[count1] = input_phrase.getText().toString();
                        count1++;
                    }
                    double endtime = LocalTime.now().toNanoOfDay();
                    double duration = endtime - startTime;
                    double seconds = duration/1000000000.0;
                    for(count2 =0; count2<store_array.length; count2++){
                        totalCha =store_array[count2].length();
                    }
                    int wpm = (int) ((((double) totalCha/5) / seconds) * 60);
                    System.out.println(wpm);
                }
            }
        });*/
    }

    public void setPhrase(Integer count){

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
		if(clickCount == 4){
            endTime = LocalTime.now().toNanoOfDay();
        }
    }
    /*public void processCurrentText() {


        for(int i =0; i<5; i++){
            text_input = input_phrase.getText().toString();
            text_input_array[i] = text_input;
          //  char_input_array[i] ;
        }

        // increment total characters typed
        characterTyped++;
        errors = 0;

        //text_displayed_array = phrase.;
        for(int i =0;i<5;i++) {
            index = phrase_array[i];
        }
        char num;
        for(char element : num) {
            char value_character = text_input_array[num];

            if (typedCharacter == null) {

            } else if (typedCharacter ==  char.innerText) {

            } else {

                errors++;
            }
        }

        char correctCharacters = (characterTyped - (total_errors + errors));
      //  char accuracyVal = ((correctCharacters / characterTyped) * 100);
       // accuracy_text



        /*if (curr_input.length == current_quote.length) {
            updateQuote();

            // update total errors
            total_errors += errors;

            // clear the input area
            input_area.value = "";
        }
    }*/
	
	public void saveInputPhrase(){

        // get current input text and split it into character array
        current_input = input_phrase.toString();
        for(int i =0;i<text_input_array.length;i++){
            text_input_array[i] = current_input;
            input_character_array = text_input_array[i].toCharArray();
        }
        // increment total characters typed
        for(char c : input_character_array){
            characterTyped = characterTyped + c;
        }
    }

    public void calculateWordsPerMinute(){

        double duration = endTime - startTime;
        double seconds = duration/1000000000.0;

        int wpm = (int) (duration/seconds);
        System.out.println(wpm);
    }
    public void calculateErrorRate(){
        String difference;
        for(int i =0;i<5;i++){
           char[] actualPhrase = phrase_array[i].toCharArray();
           if(actualPhrase[i] == input_character_array[i]){

           }
        }
    }
	
	
	
	
}