package com.example.wildusers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wildusers.Database.DBContract;
import com.example.wildusers.Database.LocalDB.DBHelper;
import com.example.wildusers.Database.OnlineDB.Api.ExperimentApi;
import com.example.wildusers.Database.OnlineDB.Model.User;
import com.example.wildusers.Database.OnlineDB.Model.W_Experiment;
import com.example.wildusers.Database.OnlineDB.RequestHandler;
import com.example.wildusers.Database.mySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class activity_sample_text1_3 extends AppCompatActivity {

    //DBHandler dbHandler;
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
    BroadcastReceiver broadcastReceiver;
    ArrayList phrases = new ArrayList<String>();
    String [] timestamps;
    CountDownTimer countDown;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private int request_code =1, FILE_SELECT_CODE =101;
    public String  actualfilepath="";
    private static int RESULT_LOAD_IMAGE = 100;


    //server impl request codes
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    //limiting sentence count declaration
    private int sentenceLimit = 3;
    private int sentenceBegin = 1;

    String newStamp;

    ArrayList<String> userEmail = new ArrayList<>();
    ArrayList<Integer> userSession = new ArrayList<>();
    Cursor cursorSession;

    List<W_Experiment> w_experiments;

    //Sample 3
    //Sessions / no of runs in each session = 10

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {

        Log.d("I am coming here", "1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_text1_3);

        w_experiments = new ArrayList<>();

        //helper = new DBHelper(this);
        helper = new DBHelper(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 10);

            }
        }

        displayText();

        //timer = (TextView) findViewById(R.id.timer);
        phrase = (TextView) findViewById(R.id.viewPhrase);
        nextBtn = (Button) findViewById(R.id.next);
        btn_play_pause = (Button) findViewById(R.id.play_pause1);
        input_phrase = (EditText) findViewById(R.id.typeText);
        sessionNo = (EditText) findViewById(R.id.SessionNo);
        submit = (Button) findViewById(R.id.sampleSubmit);
        Sentence_counter = (TextView) findViewById(R.id.count);

        /*retrieving values through intents*/
        UserID = getIntent().getStringExtra("UserID");

        //session = getIntent().getIntExtra("session",0);
        //inputMethod = getIntent().getStringExtra("inputMethod");
        session = getIntent().getIntExtra("session", 0);
//        if (session != 0) {
//            count = session;
//        }


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



                //session = Integer.parseInt(sessionNo.getText().toString());
                session = Integer.parseInt(sessionNo.getText().toString());


                response = input_phrase.getText().toString();
                stimulus = phrase.getText().toString();
                editDistance = levenshteinDistance(input_phrase.getText().toString(), phrase.getText().toString());
                duration = Duration.between(dateTime1, dateTime2).toNanos();
                /*create an object to store the data using 'Experiment ' class*/
                experiment = new Experiment(timestamps, duration, stimulus, response, editDistance);


                phraseArray_Iterator();


                //Display the sentence count
                //sentenceCount++;
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
                Intent intent = new Intent(getApplicationContext(), rating.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("session", session);
                //intent.putExtra("noOfRuns", noOfRuns);
                startActivity(intent);

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveToLocalStorage(Experiment experimentList){
        SQLiteDatabase database = helper.getWritableDatabase();
        helper.saveToLocalDatabase(UserID, sentenceCount, session, experimentList, database);
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

/**************************************************Server Implementation*********************/


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


    //Calling the create hero API
//    PerformNetworkRequest request = new PerformNetworkRequest(ExperimentApi.URL_CREATE_EXPERIMENT, params, CODE_POST_REQUEST);
//    request.execute();
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
                //refreshing the herolist after every operation
                //so we get an updated list
                //we will create this method right now it is commented
                //because we haven't created it yet
                //refreshHeroList(object.getJSONArray("heroes"));
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