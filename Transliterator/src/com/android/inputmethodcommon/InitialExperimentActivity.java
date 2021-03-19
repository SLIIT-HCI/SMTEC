package com.example.smtec;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.sql.Timestamp;
import java.text.BreakIterator;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class InitialExperimentActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Experiment> list = new ArrayList<>();

    TextView timer;
    static TextView phrase;
    Button btn_next ,btn_play_pause, btn_stop;
    static EditText input_phrase;
    Boolean clicked = false;
    int clickCount = 0;
    long timeleft;
    String [] phrase_array = new String[5];
    String [] save_phrase_array = new String[phrase_array.length];
    char [] charctersTyped_array = new char[phrase_array.length];
    int editDistance;

    double startTime,endTime;
    static String phrase1 , phrase2;
    ArrayList phrases = new ArrayList<String>();

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
        dbHelper = new DatabaseHelper(this);
        dbHelper.insertData_Phrases();

        timer = findViewById(R.id.timer_view);
        phrase = findViewById(R.id.id_sentence);
        btn_next = findViewById(R.id.btn_next);
        btn_play_pause = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);
        btn_stop.setEnabled(false);
        input_phrase = findViewById(R.id.text_enter);

        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);
        adapter = new RecyclerAdapter(list);

        readFromLocalStorage();


        timer.setText("         Let's start !");

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
                   //     db.insertData_Experiment(phrase.toString(),input_phrase.toString(),editDistance);
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
    /* public void saveInputPhrase(){

        String inputText = input_phrase.getText().toString();
        db.insertData_Experiment(inputText);
    }*/
	
	/* save to local database*/
   /* public void saveToLocalStorage(int userId,String email,String duration,String s1,String s2,int editDistance,int id){

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        if(checkNetworkConnection()){

        }else{
           dbHelper.saveToLocalDatabase(userId,email,duration,s1,s2,editDistance,id,dbHelper.SYNC_STATUS_FAILED,database);
        }
        readFromLocalStorage();
        dbHelper.close();
    }*/
	
	public void saveToAppServer(final int userId, final String email, final String duration, final String s1, final String s2, final int editDistance, final int id){

        if(checkNetworkConnection()){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DatabaseHelper.SERVER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if(response.equals("OK")){
                                    saveToLocalStorage(userId,email,duration,s1,s2,editDistance,id,DatabaseHelper.SYNC_STATUS_OK);
                                }
                                else{
                                    saveToLocalStorage(userId,email,duration,s1,s2,editDistance,id,DatabaseHelper.SYNC_STATUS_FAILED);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    saveToLocalStorage(userId,email,duration,s1,s2,editDistance,id,DatabaseHelper.SYNC_STATUS_FAILED);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("userId","userId");
                    params.put("email","email");
                    params.put("duration","duration");
                    params.put("s1","s1");
                    params.put("s2","s2");
                    params.put("editDistance","editDistance");
                    params.put("id","id");

                    return params;
                }
            };
                MySingleton.getInstance(InitialExperimentActivity.this).addToRequestQueue(stringRequest);

        }else{
            saveToLocalStorage(userId,email,duration,s1,s2,editDistance,id,DatabaseHelper.SYNC_STATUS_FAILED);
        }

    }

    /* read data from the local database*/
    private void readFromLocalStorage(){

        list.clear();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readFromLocalDatabase(database);
        while (cursor.moveToNext()){
            int userId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN1_userID));
            String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN1_email));
            String duration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN2_Duration));
            String s1 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN3_S1));
            String s2 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN4_S2));
            int editDistance = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN5_EditDistance));
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN6_ID));
            int sync_status = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN7_SYNC_STATUS));

            list.add(new Experiment(userId,email,duration,s1,s2,editDistance,id,sync_status));
        }
        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();
    }


    /* checking Internet connection */
    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        return (networkinfo != null && networkinfo.isConnected());
    }
	
	private void saveToLocalStorage(int userId,String email,String duration,String s1,String s2,int editDistance,int id,int sync_status){

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(userId,email,duration,s1,s2,editDistance,id,sync_status,database);
        readFromLocalStorage();
        dbHelper.close();
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