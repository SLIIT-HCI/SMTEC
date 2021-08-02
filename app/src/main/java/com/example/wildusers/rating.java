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
    TextView brk_timer;
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


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratings = new ArrayList<>();

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        //SharedPreferences sharedPreferences = getActivity

        rb5_speed = (RatingBar) findViewById(R.id.ratingBar5);
        rb6_accuracy = (RatingBar) findViewById(R.id.ratingBar6);
        rb7_easeOfUse = (RatingBar) findViewById(R.id.ratingBar7);

        type = (RadioGroup) findViewById(R.id.type);
        postureRG = (RadioGroup) findViewById(R.id.postureRG);
        OpenComment = findViewById(R.id.commentEdit);
        submit = (Button)findViewById(R.id.rate2SubmitBTN);


        UserID = getIntent().getStringExtra("UserID");
        session = getIntent().getIntExtra("session",0);


        int x = 0;

        dbHelper = new DBHelper(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                lastCheck = pref.getInt("countValue", 0);

                dayCount = pref.getInt("dayCount", 0);

                lastCheck++;

                System.out.println("incremented "+ lastCheck);


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

                if(lastCheck < 2){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            editor.putInt("countValue", lastCheck);
                            editor.commit();

                            Intent intent = new Intent(rating.this, alertScreen.class);
                            intent.putExtra("UserID", UserID);
                            intent.putExtra("session", session);
                            startActivity(intent);


                        }
                    }, 1000 * 5);
                }else if (lastCheck == 2 && dayCount <= 10){

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String str = sdf.format(new Date());

                    System.out.println("See you tomorrow!!! " + str);

                    int secondsToGo = getSecondsToNextDay(3);

                    System.out.println(secondsToGo);

                    //no of times activity iterated
                    lastCheck = 0;

                    dayCount++;

                    editor.putInt("countValue", lastCheck);
                    editor.commit();

                    editor.putInt("dayCount", dayCount);
                    editor.commit();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            String str = sdf.format(new Date());

                            System.out.println("welcome back!!! " + str);


                            Intent intent = new Intent(rating.this, alertScreen.class);
                            intent.putExtra("UserID", UserID);
                            intent.putExtra("session", session);
                            startActivity(intent);


                            //move the activity to background
//                            moveTaskToBack(true);
//
                        }
                    }, 600000);
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
//        PerformNetworkRequest request = new PerformNetworkRequest(RatingApi.URL_CREATE_RATING, params, CODE_POST_REQUEST);
//        request.execute();
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
            //progressBar.setVisibility(View.VISIBLE);
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