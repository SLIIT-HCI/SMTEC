package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wildusers.Database.LocalDB.DBHelper;
import com.example.wildusers.Database.OnlineDB.Api.RatingApi;
import com.example.wildusers.Database.OnlineDB.Model.W_Rating;
import com.example.wildusers.Database.OnlineDB.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class rating extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    //array to store ratings data in aws server
    List<W_Rating> ratings;


    DBHelper dbHelper;
    RatingBar rb5_speed, rb6_accuracy, rb7_easeOfUse;
    RadioGroup type, postureRG;
    RadioButton Type_radioBtn, TypeHandPosture;
    EditText OpenComment;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.S");
    LocalDateTime dateTime1,dateTime2;
    String UserID;
    String comment;
    float speed, accuracy, easeOfUse;
    String preference, HandPosture;
    int lastCheck = 0;
    int dayCount = 0;
    int session;
    Button submit;
    String roundHourString,roundMinuteString;


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratings = new ArrayList<>();

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        rb5_speed = (RatingBar) findViewById(R.id.ratingBar5);
        rb6_accuracy = (RatingBar) findViewById(R.id.ratingBar6);
        rb7_easeOfUse = (RatingBar) findViewById(R.id.ratingBar7);
        type = (RadioGroup) findViewById(R.id.type);
        postureRG = (RadioGroup) findViewById(R.id.postureRG);
        OpenComment = findViewById(R.id.commentEdit);
        submit = (Button)findViewById(R.id.rate2SubmitBTN);

        UserID = pref.getString("UserID","");
        session = getIntent().getIntExtra("session",0);

        dbHelper = new DBHelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lastCheck = pref.getInt("countValue", 0);
                dayCount = pref.getInt("dayCount", 0);

                lastCheck++;

                roundHourString = "HoursRound"+lastCheck;
                roundMinuteString = "MinutesRound"+lastCheck;

                int selectedIDPreference = type.getCheckedRadioButtonId();
                Type_radioBtn = (RadioButton) findViewById(selectedIDPreference);
                preference = (String) Type_radioBtn.getText().toString();

                int selectedIDHandPosture = postureRG.getCheckedRadioButtonId();
                TypeHandPosture = (RadioButton) findViewById(selectedIDHandPosture);
                HandPosture = (String) TypeHandPosture.getText().toString();

                speed = rb5_speed.getRating();
                accuracy = rb6_accuracy.getRating();
                easeOfUse = rb7_easeOfUse.getRating();
                comment = OpenComment.getText().toString();


                saveToDatabase(speed, accuracy, preference, easeOfUse, HandPosture, comment);
                createRating();

                //move the activity to background
                moveTaskToBack(true);

                if(lastCheck < 10){

                    int hourToStart = pref.getInt("HoursRound1",0);
                    int minutesToStart = pref.getInt("MinutesRound1",0);


                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourToStart);
                    calendar.set(Calendar.MINUTE, minutesToStart);
                    calendar.set(Calendar.SECOND, 0);

                    Alarm.startAlarm(calendar,getApplicationContext());

                }else if (lastCheck == 10 && dayCount <= 2){

                    lastCheck = 0;

                    dayCount++;

                    editor.putInt("countValue", lastCheck);
                    editor.commit();

                    editor.putInt("dayCount", dayCount);
                    editor.commit();


                    int hourToStart = pref.getInt("HoursRound0",0);
                    int minutesToStart = pref.getInt("MinutesRound0",0);


                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourToStart);
                    calendar.set(Calendar.MINUTE, minutesToStart);
                    calendar.set(Calendar.SECOND, 0);

                    Alarm.startAlarm(calendar,getApplicationContext());

                }
                else{
                    Intent i = new Intent(getApplicationContext(), questionnaire.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    public int getSecondsToNextDay(int startHour){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String str = sdf.format(new Date());

        int hoursToGo = 0;
        int secondsToGo = 0;

        int currentHour = Integer.parseInt(str);
        System.out.println(str);

        if(currentHour > 0 && currentHour < 12){
            hoursToGo = startHour - currentHour;
        }else if(currentHour > 12){
            hoursToGo = (startHour + 24) - currentHour;
        }

        secondsToGo = hoursToGo*3600;

        return secondsToGo;
    }

    /***************************** to xampp server *****************************/
    private void createRating(){

        HashMap<String, String> params = new HashMap<>();

        params.put("user_id", UserID);
        params.put("session", String.valueOf(session));
        params.put("speed", String.valueOf(speed));
        params.put("accuracy", String.valueOf(accuracy));
        params.put("preference", String.valueOf(preference));
        params.put("easeOfUse", String.valueOf(easeOfUse));
        params.put("handPosture", String.valueOf(HandPosture));
        params.put("comment", comment);


        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(RatingApi.URL_CREATE_RATING, params, CODE_POST_REQUEST);
        request.execute();
    }




    public void saveToDatabase(float speed,float accuracy, String preference,float easeOfUse, String HandPosture, String comment){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToRatingsTable(UserID, session, speed,accuracy,preference,easeOfUse, HandPosture, comment, database);
    }


    /*********************************** class to perform network request - online db impl**************************/
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
            //progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the rating list after every operation
                    //so we get an updated list
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