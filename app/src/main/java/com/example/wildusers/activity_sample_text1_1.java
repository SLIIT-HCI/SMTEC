package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wildusers.Database.DBContract;
import com.example.wildusers.Database.DatabaseHelper;
import com.example.wildusers.Database.mySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class activity_sample_text1_1 extends AppCompatActivity {


    DatabaseHelper dbHelper;
    ImageView next;
    EditText text1;
    TextView phrase1, timer1;
    Button start1;

    Boolean clicked = false;
    long timeleft;
    double endTime;
    int clickCount = 0;

    BroadcastReceiver broadcastReceiver;

    RecyclerAdapter adapter;
    ArrayList<String> phrases = new ArrayList<>();
    ArrayList<Experiment> list = new ArrayList<>();

    /***************************************format time****************************************/

    public String formatTime(long millis){
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text1_1);


        next = (ImageView) findViewById(R.id.next);
        text1 = (EditText) findViewById(R.id.typeText);
        phrase1 = (TextView) findViewById(R.id.text1);
        start1 = (Button) findViewById(R.id.startBTN);
        timer1 = (TextView) findViewById(R.id.timer1);


        adapter = new RecyclerAdapter(list);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                readFromLocalStorage();
            }
        };

        timer1.setText("        Let's start !");



        /***********************************Count Down*********************************************/
        final CountDownTimer countDown = new CountDownTimer(60000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                if(clicked == false) {
                    setPhrase(0);
                }
                timer1.setText("Time remaining: " + formatTime(millisUntilFinished));
                timeleft = millisUntilFinished/1000;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onFinish()
            {
                endTime = LocalTime.now().toNanoOfDay();
                timer1.setText("Your Time is over !");
                phrase1.setText("");
                text1.setText("");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        /**************************************end of count down***********************************/



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_sample_text1_1.this, activity_sample_text1_2.class);
                startActivity(i);
                clicked = true;
                phraseArray_Iterator();
            }
        });

/************************************    constructing    ******************************************/
        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.start();
                start1.setEnabled(false);
                //start1.setBackgroundResource(R.drawable.btn_play_gray);
                //btn_stop.setEnabled(true);
                setPhrase(0);
            }
        });
    }




//    public void setPhrase(Integer count){
//
//        /*phrase_array[0] ="You are ready to learn and do your best, but you are also nervous.";
//        phrase_array[1] = "Sometimes the most difficult questions have the simplest solutions";
//        phrase_array[2] = "Congratulations on your new job";
//        phrase_array[3] = "Starting a new job is exciting but stressful.";
//        phrase_array[4] = "Tomorrow is second Saturday.";
//        phrase.setText(phrase_array[count]); */
//
//        List<String> allTexts = Phrases_from_Database.getInputTexts();
//
//            for(Integer index = 0 ; index < allTexts.size() ; index ++){
//                phrase_array[index] = allTexts.get(index);
//            }
//
//        phrase1.setText(phrase_array[count]);
//
//        phrases = getPhrasesFromAppServer("http://192.168.1.4/smtec/smtec.php");
//
//        phrase1.setText(phrases[count]);
//    }

//    public void setPhrase(int count){
//
//     /* phrases = dbHelper.getPhrases();
//      System.out.println("phrases: "+phrases.get(0));
//       if(!phrases.isEmpty() && ((phrases.size()-1) >= count)) {
//            phrase.setText(phrases.get(count).toString());
//       }  */
//
//        phrases = getPhrasesFromAppServer("http://192.168.1.4/smtec/smtec.php");
//
//        phrase1.setText(phrases[count]);
//    }


    public void setPhrase(int count){

        //phrases = getPhrasesFromAppServer("http://192.168.1.4/smtec/smtec.php");
        //System.out.println("phrases: "+phrases.get(0));
        // phrase.setText(phrases[count]);

        phrases = dbHelper.getPhrases();
        phrases = dbHelper.getPhrases();
        System.out.println("phrases: "+phrases.get(0));
        if(!phrases.isEmpty() && ((phrases.size()-1) >= count)) {
            phrase1.setText(phrases.get(count).toString());
        }

    }
    public void phraseArray_Iterator(){

        clickCount = clickCount + 1;
        if(clickCount > 9){
            clickCount = 0;
        }
        setPhrase(clickCount);
        text1.setText("");

    }


    /* save to local database*/
    public void saveToAppServer(final String email, final int session, final String duration, final String s1, final String s2, final int editDistance, final int status, final String sensorType, final double val_x, final double val_y, final double val_z){

  //      if(checkNetworkConnection()){

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
            mySingleton.getInstance(activity_sample_text1_1.this).addToRequestQueue(stringRequest);

//        }else{
//            saveToLocalStorage(email,session,duration,s1,s2,editDistance,DatabaseHelper.SYNC_STATUS_FAILED,sensorType,val_x,val_y,val_z);
//        }

    }


    private void getPhrasesFromAppServer() {

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

/*****************/
           // list.add(new Experiment(email,session,duration,s1,s2,editDistance,sync_status,sensorType,value_x,value_y,value_z));
        }
        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();
    }
/************/
    /* checking Internet connection */
//    public boolean checkNetworkConnection(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
//        return (networkinfo != null && networkinfo.isConnected());
//    }

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
/***************/
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //registerReceiver(broadcastReceiver, new IntentFilter(DBContract.UI_UPDATE_BROADCAST));
//    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }



















//
//    private String[] loadIntoTextView(String json) throws JSONException {
//        JSONArray jsonArray = new JSONArray(json);
//        String[] phrases = new String[jsonArray.length()];
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject obj = jsonArray.getJSONObject(i);
//            phrases[i] = obj.getString("phrase");
//        }
//        //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, phrases);
//        // phrase.setText((CharSequence) arrayAdapter);
//        return phrases;
//    }
//
//    private String[] getPhrasesFromAppServer(final String urlWebService) {
//
//        final String[] value = new String[30];
//        class GetJSON extends AsyncTask<Void, Void, String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//                try {
//                    for(String text : loadIntoTextView(s)){
//                        for(int i =0;i<value.length;i++){
//                            value[i] = text;
//                        }
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                try {
//                    URL url = new URL(urlWebService);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    String json;
//                    while ((json = bufferedReader.readLine()) != null) {
//                        sb.append(json + "\n");
//                    }
//                    return sb.toString().trim();
//                } catch (Exception e) {
//                    return null;
//                }
//            }
//        }
//        GetJSON getJSON = new GetJSON();
//        getJSON.execute();
//
//        return value;
//    }
}