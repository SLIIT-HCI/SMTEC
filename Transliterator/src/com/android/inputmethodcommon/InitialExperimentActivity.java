package com.example.smtec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    int editDistance;
    BroadcastReceiver broadcastReceiver;
    double endTime;
    String email;
    int session;
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

        timer = (TextView) findViewById(R.id.timer_view);
        phrase = (TextView) findViewById(R.id.id_sentence);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_play_pause = (Button) findViewById(R.id.btn_play);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setEnabled(false);
        input_phrase = (EditText) findViewById(R.id.text_enter);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        email = getIntent().getStringExtra("email");
        session = getIntent().getIntExtra("session",0);

        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);
        adapter = new RecyclerAdapter(list);
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
                      //  saveToAppServer(email,session,duration,phrase.toString(),input_phrase.toString(),editDistance,id);
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

    /* save to local database*/
    public void saveToAppServer(final String email, final int session, final String duration, final String s1, final String s2, final int editDistance, final int status, final String sensorType, final double val_x, final double val_y, final double val_z){

        if(checkNetworkConnection()){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DatabaseHelper.SERVER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if(response.equals("OK")){
                                    saveToLocalStorage(email,session,duration,s1,s2,editDistance,DatabaseHelper.SYNC_STATUS_OK,sensorType,val_x,val_y,val_z);
                                }
                                else{
                                    saveToLocalStorage(email,session,duration,s1,s2,editDistance,DatabaseHelper.SYNC_STATUS_FAILED,sensorType,val_x,val_y,val_z);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    saveToLocalStorage(email,session,duration,s1,s2,editDistance,DatabaseHelper.SYNC_STATUS_FAILED,sensorType,val_x,val_y,val_z);
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("session","session");
                    params.put("email","email");
                    params.put("duration","duration");
                    params.put("s1","s1");
                    params.put("s2","s2");
                    params.put("editDistance","editDistance");
                    params.put("sensorType","sensorType");
                    params.put("value_x","value_x");
                    params.put("value_y","value_y");
                    params.put("value_z","value_z");

                    return params;
                }
            };
                MySingleton.getInstance(InitialExperimentActivity.this).addToRequestQueue(stringRequest);

        }else{
            saveToLocalStorage(email,session,duration,s1,s2,editDistance,DatabaseHelper.SYNC_STATUS_FAILED,sensorType,val_x,val_y,val_z);
        }

    }

    /* read data from the local database*/
    private void readFromLocalStorage(){

        list.clear();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readFromLocalDatabase(database);
        while (cursor.moveToNext()){
            int session = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN1_session));
            String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN1_email));
            String duration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN2_Duration));
            String s1 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN3_S1));
            String s2 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN4_S2));
            int editDistance = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN5_EditDistance));
            int sync_status = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN6_SYNC_STATUS));
            String sensorType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN7_SENSOR_NAME));
            double value_x = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN8_X));
            double value_y = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN9_Y));
            double value_z = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN10_Z));

            list.add(new Experiment(email,session,duration,s1,s2,editDistance,sync_status,sensorType,value_x,value_y,value_z));
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

    private void saveToLocalStorage(String email,int session,String duration,String s1,String s2,int editDistance,int sync_status,String sensorName,double val_x,double val_y,double val_z){

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(email,session,duration,s1,s2,editDistance,sensorName,val_x,val_y,val_z,sync_status,database);
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
        registerReceiver(broadcastReceiver, new IntentFilter(Dbcontract.UI_UPDATE_BROADCAST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}