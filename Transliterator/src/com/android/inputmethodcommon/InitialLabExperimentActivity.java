package com.example.smtec_labuserexperiment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class InitialLabExperimentActivity extends AppCompatActivity {

    DBHelper dbHelper;

    TextView timer;
    TextView phrase;
    Button btn_next ,btn_play_pause, btn_stop;
    EditText input_phrase;
    Boolean clicked = false;
    int clickCount = 0;
    long timeleft;

    int editDistance, session , duration;
    String email , formatDateTime, text_displayed, text_input;
    LocalDateTime dateTime;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH.mm");
    BroadcastReceiver broadcastReceiver;

    ArrayList phrases = new ArrayList<String>();
    ArrayList<Experiment> experimentList = new ArrayList<>();
    recyclerAdapter_lab adapter;

    String [] timestamps;

    CountDownTimer countDown;

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

        Log.d("I am coming here","1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_experiment);
        dbHelper = new DBHelper(this);
        dbHelper.insertData_Phrases();

        timer = findViewById(R.id.timer_view);
        phrase = findViewById(R.id.id_sentence);
        btn_next = findViewById(R.id.btn_next);
        btn_play_pause = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);
        btn_stop.setEnabled(false);
        input_phrase = findViewById(R.id.text_enter);


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               readFromLocalStorage();
            }
        };

        readFromLocalStorage();

        timer.setText("        Let's start !");

        final CountDownTimer countDown = new CountDownTimer(60000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
               if(clicked == false) {
                   //setPhrase(0);
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
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.start();
                btn_play_pause.setEnabled(false);
                btn_play_pause.setBackgroundResource(R.drawable.btn_play_gray);
                btn_stop.setEnabled(true);
                setPhrase(0);
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.cancel();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
               clicked = true;
               phraseArray_Iterator();
            }
        });
        input_phrase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    while(btn_next.isPressed() && timeleft!=0){
                       // saveInputPhrase();
                        editDistance = levenshteinDistance(phrase.toString(),input_phrase.toString());
                   //     db.saveToLocalDatabase(phrase.toString(),input_phrase.toString(),editDistance);
                    }
                }
            }
        });
    }

    public void setPhrase(int count){

      phrases = dbHelper.getPhrases();
      System.out.println("phrases: "+phrases.get(0));
       if(!phrases.isEmpty() && ((phrases.size()-1) >= count)) {
            phrase.setText(phrases.get(count).toString());
       }
    }
    public void phraseArray_Iterator(){

        clickCount = clickCount + 1;
        if(clickCount > 9){
            clickCount = 0;
        }
        setPhrase(clickCount);
        input_phrase.setText("");
    }

    /* read data from the local database*/
    private void readFromLocalStorage(){

        list.clear();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readFromLocalDatabase(database);
        while (cursor.moveToNext()){
            int userId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN1_userID));
            String email = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN1_email));
            String duration = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN2_Duration));
            String s1 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN3_S1));
            String s2 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN4_S2));
            int editDistance = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN5_EditDistance));

            list.add(new LabExperiment(userId,email,duration,s1,s2,editDistance));
        }
        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();
    }

    private void saveToLocalStorage(int userId,String email,String duration,String s1,String s2,int editDistance,int id,int sync_status){package com.example.labexperiment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labexperiment.DBHelper;
import com.example.labexperiment.Databasecontract;
import com.example.labexperiment.R;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InitialExperiment extends AppCompatActivity {

    DBHelper dbHelper;

    TextView timer;
    TextView phrase;
    Button btn_next ,btn_play_pause, btn_stop;
    EditText input_phrase;
    Boolean clicked = false;
    int clickCount = 0;
    long timeleft;

    int editDistance, session , duration;
    String email , formatDateTime, text_displayed, text_input;
    LocalDateTime dateTime;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH.mm");
    BroadcastReceiver broadcastReceiver;

    ArrayList phrases = new ArrayList<String>();
    ArrayList<Experiment> experimentList = new ArrayList<>();
    recyclerAdapter_lab adapter;

    String [] timestamps;

    CountDownTimer countDown;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {

        //Log.d("I am coming here","1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_experiment);

        dbHelper = new DBHelper(this);
        dbHelper.insertData_Phrases();

        timer = (TextView) findViewById(R.id.timer_view);
        phrase = (TextView) findViewById(R.id.id_sentence);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_play_pause = (Button) findViewById(R.id.btn_play);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setEnabled(false);
        input_phrase = (EditText) findViewById(R.id.text_enter);

        email = getIntent().getStringExtra("email");
        session = getIntent().getIntExtra("session",0);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
             //   readFromLocalStorage();
            }
        };

        timer.setText("        Let's start !");
        startTimer();

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countDown.start();
                btn_play_pause.setEnabled(false);
                btn_play_pause.setBackgroundResource(R.drawable.btn_play_gray);
                btn_stop.setEnabled(true);
                setPhrase(0);

               // System.out.println("Input" + input_phrase.getText().toString());
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.cancel();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                clicked = true;
                System.out.println("I am here");
                String response = input_phrase.getText().toString();
                String stimulus = phrase.getText().toString();
                int Editdistance = levenshteinDistance( input_phrase.getText().toString(), phrase.getText().toString());

                experimentList.add(new Experiment(email,session,timestamps,stimulus,response,Editdistance));
                for(int i=0;i<experimentList.size();i++){
                    System.out.println("Values " + experimentList.get(i).getEmail());
                    System.out.println("Values " + experimentList.get(i).getSession());
                    System.out.println("Values " + experimentList.get(i).getStimulus());
                    System.out.println("Values " + experimentList.get(i).getResponse());
                    System.out.println("Values " + experimentList.get(i).getEditDistance());
                    System.out.println("Values " + Arrays.toString(experimentList.get(i).getDurationTimeStamps()));
                }
                phraseArray_Iterator();
            }
        });
        final TextWatcher noOfTaps = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timestamps = new String[s.length()];
                for(int i=0;i<s.length();i++){
                    dateTime = LocalDateTime.now();
                    formatDateTime = dateTime.format(formatDate);
                    timestamps[i] = formatDateTime;
                }
            }
            public void afterTextChanged(Editable s) {

            }
        };
        input_phrase.addTextChangedListener(noOfTaps);
    }


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
                if(clicked == false) {
                    //setPhrase(0);
                }
                timer.setText("Time remaining: " + formatTime(millisUntilFinished));
                timeleft = millisUntilFinished/1000;
            }
            @RequiresApi(api = Build.VERSION_CODES.O)

            public void onFinish()
            {
                saveToLocalStorage(experimentList);
                timer.setText("Your Time is over !");
                phrase.setText("");
                input_phrase.setEnabled(false);
                input_phrase.setText("");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

    }

    public void setPhrase(int count){

        phrases = dbHelper.getPhrases();
        System.out.println("phrases: "+phrases.get(0));
        if(!phrases.isEmpty() && ((phrases.size()-1) >= count)) {
            phrase.setText(phrases.get(count).toString());
        }
    }
    public void phraseArray_Iterator(){

        clickCount = clickCount + 1;
        if(clickCount > 9){
            clickCount = 0;
        }
        setPhrase(clickCount);
        input_phrase.setText("");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)

    private void saveToLocalStorage(ArrayList<Experiment> experimentList){

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(experimentList,database);

        dbHelper.close();
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

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(userId,email,duration,s1,s2,editDistance,id,sync_status,database);
        readFromLocalStorage();
        dbHelper.close();
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