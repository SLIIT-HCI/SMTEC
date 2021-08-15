package com.example.wildusers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wildusers.Database.LocalDB.DBHelper;
import com.example.wildusers.Database.OnlineDB.Api.ExperimentApi;
import com.example.wildusers.Database.OnlineDB.Model.W_Experiment;
import com.example.wildusers.Database.OnlineDB.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.O)
public class activity_sample_text1_3 extends AppCompatActivity {

    DBHelper helper;
    Experiment experiment;
    TextView phrase, Sentence_counter;
    Button btn_play_pause;
    Button submit;
    EditText input_phrase, sessionNo;
    Button nextBtn;
    Boolean clicked = false;
    int editDistance, clickCount;
    public int session;
    private int sentenceCount = 1;
    long duration;
    String response, stimulus, UserID , formatDateTime1,formatDateTime2;
    Set<Integer> generated = new HashSet<>();
    int randomMax = 80;
    LocalDateTime dateTime1,dateTime2;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.S");
    ArrayList phrases = new ArrayList<String>();
    String [] timestamps;
    private SharedPreferences pref;
    private int sentenceLimit = 3;
    private int sentenceBegin = 1;
    boolean isAllFieldsChecked = false;
    String newStamp;
    List<W_Experiment> w_experiments;

    //server impl request codes
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Log.d("I am coming here", "1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text1_3);

        w_experiments = new ArrayList<>();

        helper = new DBHelper(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 10);
            }
        }

        displayText();

        phrase = (TextView) findViewById(R.id.viewPhrase);
        nextBtn = (Button) findViewById(R.id.next);
        btn_play_pause = (Button) findViewById(R.id.play_pause1);
        input_phrase = (EditText) findViewById(R.id.typeText);
        sessionNo = (EditText) findViewById(R.id.SessionNo);
        submit = (Button) findViewById(R.id.sampleSubmit);
        Sentence_counter = (TextView) findViewById(R.id.count);

        UserID = pref.getString("UserID","");
        session = getIntent().getIntExtra("session", 0);

        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startTimer();
                btn_play_pause.setEnabled(false);
                btn_play_pause.setBackgroundResource(R.drawable.btn_play_gray);
                setPhrase(0);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                clicked = true;
                dateTime2 = LocalDateTime.now();
                formatDateTime2 = dateTime2.format(formatDate);
                timestamps[timestamps.length - 1] = formatDateTime2;

                session = Integer.parseInt(sessionNo.getText().toString());
                response = input_phrase.getText().toString();
                stimulus = phrase.getText().toString();
                editDistance = levenshteinDistance(input_phrase.getText().toString(), phrase.getText().toString());
                duration = Duration.between(dateTime1, dateTime2).toNanos();

                /*create an object to store the data using 'Experiment ' class*/
                experiment = new Experiment(timestamps, duration, stimulus, response, editDistance);

                phraseArray_Iterator();

                Sentence_counter.setText(Integer.toString(sentenceCount));
                sentenceCount = Integer.parseInt(Sentence_counter.getText().toString());
                newStamp = formatDateTime1 + " , "+ formatDateTime2;
                saveToLocalStorage(experiment);
                createExperiment();
                sentenceCount++;

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = validate();
                if(isAllFieldsChecked){
                    Intent intent = new Intent(getApplicationContext(), rating.class);
                    intent.putExtra("UserID", UserID);
                    intent.putExtra("session", session);
                    startActivity(intent);
                    finish();
                }
            }
        });

        /*getting timestamp for the first character entered*/
        final TextWatcher noOfTaps = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timestamps = new String[2];
                for (int i = 0; i < s.length(); i++) {
                    dateTime1 = LocalDateTime.now();
                    formatDateTime1 = dateTime1.format(formatDate);
                    timestamps[0] = formatDateTime1;
                }
            }

            public void afterTextChanged(Editable s) {
            }
        };
        input_phrase.addTextChangedListener(noOfTaps);

    }
/**
 * Validating the session number field in the activity
 * */
    private boolean validate(){
        //boolean valid = true;
        if(sessionNo.toString().length() == 0){
            sessionNo.setError("Please Enter Session Number");
            return false;
        }
        else{
            return true;
        }
    }

    public void displayText() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("phrases.txt")));
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                helper.insertData_Phrases(mLine);
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
        phrases = helper.getPhrases();
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

        if (sentenceLimit < sentenceBegin) {
            phrase.setText("");
            nextBtn.setEnabled(false);
            Sentence_counter.setText("");
        }
        sentenceBegin++;
    }

/**
 * Store Experiment data to SQLite Database
 */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveToLocalStorage(Experiment experimentList){
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.saveToLocalDatabase(UserID, sentenceCount, session, experimentList, database);
    }

/**
 * Levenshtein Distance to calculate the edit distance
 * */
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

/**
 * Server implementation to upload the data to AWS server
 * */
    private void createExperiment(){
        //validating the inputs
        if (TextUtils.isEmpty(String.valueOf(session))) {
            sessionNo.setError("Please enter session");
            sessionNo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(response)) {
            input_phrase.setError("Please enter response");
            input_phrase.requestFocus();
            return;
        }
        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", UserID);

        params.put("session", String.valueOf(session));
        System.out.println("Checking................."+session);

        params.put("date", newStamp);
        System.out.println("Time Stamps................."+newStamp);

        params.put("stimulus", stimulus);
        System.out.println("stimulus................."+stimulus);

        params.put("response", response);
        System.out.println("Checking................." + response);

        params.put("edit_distance", String.valueOf(editDistance));
        System.out.println("Checking................." + editDistance);

        params.put("sentenceNo", String.valueOf(sentenceCount));
        System.out.println("Checking................."+sentenceCount);

        params.put("duration", String.valueOf(duration));
        System.out.println("Checking................."+duration);

        //Calling the create experiment API
        PerformNetworkRequest request = new PerformNetworkRequest(ExperimentApi.URL_CREATE_EXPERIMENT, params, CODE_POST_REQUEST);
        request.execute();
        System.out.println("Executed!!");
    }

    //inner class to perform network request extending an AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);

            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }


}