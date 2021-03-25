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
    static TextView phrase;
    Button btn_next ,btn_play_pause, btn_stop;
    static EditText input_phrase;
    Boolean clicked = false;
    int clickCount = 0;
    long timeleft;
    String [] phrase_array = new String[5];
    int editDistance;
    BroadcastReceiver broadcastReceiver;

    double startTime,endTime;
    ArrayList phrases = new ArrayList<String>();
    ArrayList<LabExperiment> list = new ArrayList<>();
    recyclerAdapter adapter;

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

    private void saveToLocalStorage(int userId,String email,String duration,String s1,String s2,int editDistance,int id,int sync_status){

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