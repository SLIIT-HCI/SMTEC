package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wildusers.Database.LocalDB.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@RequiresApi(api = Build.VERSION_CODES.O)
public class activity_sample_text2_3 extends AppCompatActivity {

    ImageView btn_next;
//    EditText text23;
//    TextView phrase23, Timer2;
    //FloatingActionButton btn_play_pause
//    int counting = 10;
    //Button btn_next;


    DBHelper dbHelper;
    Experiment experiment;
    TextView timer, phrase;
    Button btn_play_pause1;
    EditText input_phrase;
    Boolean clicked = false;
    int clickCount,editDistance, session, noOfRuns;
    long timeleft, duration;
    String response, stimulus, inputMethod, email , formatDateTime1,formatDateTime2;
    Set<Integer> generated = new HashSet<>();
    int randomMax = 80;
    int count = 1;
    LocalDateTime dateTime1,dateTime2;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.S");
    BroadcastReceiver broadcastReceiver;
    ArrayList phrases = new ArrayList<String>();
    String [] timestamps;
    CountDownTimer countDown;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private int request_code =1, FILE_SELECT_CODE =101;
    public String  actualfilepath="";
    private static int RESULT_LOAD_IMAGE = 100;




    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text2_3);

//        text23 = (EditText) findViewById(R.id.typeText23);
//        phrase23 = (TextView) findViewById(R.id.text23);
        //btn_next = (ImageView) findViewById(R.id.next23);
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(activity_sample_text2_3.this, activity_sample_text3_3.class);
//                startActivity(i);
//            }
//        });




        /**********************************************************************/

        dbHelper = new DBHelper(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 10);

            }
        }

        displayText();

        timer = (TextView) findViewById(R.id.timer2);
        phrase = (TextView) findViewById(R.id.RetrievePhrase2);
        btn_next = (ImageView) findViewById(R.id.next23);
        btn_play_pause1 = (Button) findViewById(R.id.play_pause2);
        input_phrase = (EditText) findViewById(R.id.typeText23);

        /*retrieving values through intents*/
        email = getIntent().getStringExtra("email");
        session = getIntent().getIntExtra("session",0);
        inputMethod = getIntent().getStringExtra("inputMethod");
        noOfRuns = getIntent().getIntExtra("noOfRuns",0);
        if(noOfRuns !=0 ){
            count = noOfRuns;
        }

        timer.setText("            Let's Start !");
        btn_play_pause1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
                btn_play_pause1.setEnabled(false);
                btn_play_pause1.setBackgroundResource(R.drawable.btn_play_gray);
                setPhrase(0);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                clicked = true;
                dateTime2 = LocalDateTime.now();
                formatDateTime2 = dateTime2.format(formatDate);
                timestamps[timestamps.length - 1] = formatDateTime2;

                response = input_phrase.getText().toString();
                stimulus = phrase.getText().toString();
                editDistance = levenshteinDistance( input_phrase.getText().toString(), phrase.getText().toString());
                duration = Duration.between(dateTime1, dateTime2).toNanos();
                /*create an object to store the data using 'Experiment ' class*/
                experiment = new Experiment(timestamps,duration,stimulus,response,editDistance);
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
                    dateTime1 = LocalDateTime.now();
                    formatDateTime1 = dateTime1.format(formatDate);
                    timestamps[0] = formatDateTime1;
                }
            }
            public void afterTextChanged(Editable s) {}
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
    // Countdown Method Invocation
    public void startTimer(){
        countDown = new CountDownTimer(60000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                timer.setText("  Time remaining: " + formatTime(millisUntilFinished));
                timeleft = millisUntilFinished/1000;
            }
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onFinish()
            {
                phrase.setText("");
                timer.setText("   Your Time is over !");
                btn_next.setEnabled(false);
                input_phrase.setEnabled(false);
                input_phrase.setText("");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(activity_sample_text2_3.this, activity_sample_text3_3.class);
                        intent.putExtra("email", email);
                        intent.putExtra("session", session);
                        intent.putExtra("noOfRuns", noOfRuns);
                        startActivity(intent);
                    }
                }, 2000);
            }
        }.start();
    }
    public void displayText() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("phrases.txt")));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                dbHelper.insertData_Phrases(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
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
        dbHelper.saveToLocalDatabase(email,session,count,experimentList,database);
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
/****************************************************************************************************/
}