package com.example.labexperiment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InitialExperiment extends AppCompatActivity {

    DBHelper dbHelper;
    Experiment experiment;
    TextView timer, brk_timer, session_Info,phrase;
    Button btn_next ,btn_play_pause;
    EditText input_phrase;
    Boolean clicked = false;
    int clickCount,editDistance, session;
    long timeleft;
    String response, stimulus, inputMethod, email , formatDateTime;
    Set<Integer> generated = new HashSet<>();
    int randomMax = 10;
    LocalDateTime dateTime;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH.mm");
    BroadcastReceiver broadcastReceiver;
    ArrayList phrases = new ArrayList<String>();
    String [] timestamps;
    CountDownTimer countDown;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_experiment);

        dbHelper = new DBHelper(this);
        dbHelper.insertData_Phrases();

        timer = (TextView) findViewById(R.id.timer_view);
        phrase = (TextView) findViewById(R.id.id_sentence);
        session_Info = (TextView) findViewById(R.id.sessionInfo);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_play_pause = (Button) findViewById(R.id.btn_play);
        brk_timer = findViewById(R.id.brk_timer);
        input_phrase = (EditText) findViewById(R.id.text_enter);

        /*retrieving values through intents*/
        email = getIntent().getStringExtra("email");
        session = getIntent().getIntExtra("session",0);
        inputMethod = getIntent().getStringExtra("inputMethod");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
             //   readFromLocalStorage();
            }
        };

        timer.setText("          Let's Start !");
        startTimer();

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
           countDown.start();
           btn_play_pause.setEnabled(false);
           btn_play_pause.setBackgroundResource(R.drawable.btn_play_gray);
           setPhrase(0);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                clicked = true;
                timestamps[timestamps.length - 1] = LocalDateTime.now().format(formatDate);
                response = input_phrase.getText().toString();
                stimulus = phrase.getText().toString();
                editDistance = levenshteinDistance( input_phrase.getText().toString(), phrase.getText().toString());
                /*create an object to store the data using 'Experiment ' class*/
                experiment = new Experiment(timestamps,stimulus,response,editDistance);
                saveToLocalStorage(experiment);
                phraseArray_Iterator();
            }
        });
        /*getting timestamp for the first character entered*/
        final TextWatcher noOfTaps = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timestamps = new String[2];
                for(int i=0;i<s.length();i++){
                    dateTime = LocalDateTime.now();
                    formatDateTime = dateTime.format(formatDate);
                    timestamps[0] = formatDateTime;
                }
            }
            public void afterTextChanged(Editable s) {}
        };

        input_phrase.addTextChangedListener(noOfTaps);
    }
    /* onCreate ends here*/

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

    public void startTimer(){
        countDown = new CountDownTimer(60000, 1000)
        {
           public void onTick(long millisUntilFinished)
           {
              timer.setText("Time remaining: " + formatTime(millisUntilFinished));
              timeleft = millisUntilFinished/1000;
           }
           @RequiresApi(api = Build.VERSION_CODES.O)
           public void onFinish()
           {
              phrase.setText("");
              timer.setText(" Your Time is over !");
              input_phrase.setEnabled(false);
              input_phrase.setText("");
           try {
               TimeUnit.SECONDS.sleep(3);
           }catch (InterruptedException e) {
               e.printStackTrace();
           }
           Intent intent = new Intent(InitialExperiment.this, BreakTimeActivity.class);
           intent.putExtra("email", email);
           intent.putExtra("session", session);
           startActivity(intent);
          }
        };
    }

    public void setPhrase(int count){
      phrases = dbHelper.getPhrases();
      if(!phrases.isEmpty() && ((phrases.size()-1) >= count)) {
          phrase.setText(phrases.get(count).toString());
      }
    }

    public void phraseArray_Iterator(){
       Random rand = new Random();
       clickCount = rand.nextInt(randomMax);
       while (generated.contains(clickCount) && generated.size() < randomMax) {
           clickCount = rand.nextInt(randomMax);
       }
       generated.add(clickCount);
       clickCount += 1;
       setPhrase(clickCount);
       input_phrase.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveToLocalStorage(Experiment experimentList){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(email,session,experimentList,inputMethod,database);
    }
    
    /* calculating edit distance */
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

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(Databasecontract.UI_UPDATE_BROADCAST));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}