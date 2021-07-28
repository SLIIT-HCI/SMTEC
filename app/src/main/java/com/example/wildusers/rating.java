package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
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
import com.example.wildusers.Database.OnlineDB.Model.User;
import com.example.wildusers.Database.OnlineDB.Model.W_Rating;
import com.example.wildusers.Database.OnlineDB.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class rating extends AppCompatActivity {


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    List<W_Rating> ratings;

    DBHelper dbHelper;
    RatingBar rb5_speed, rb6_accuracy, rb7_easeOfUse;
    RadioGroup type, postureRG;
    RadioButton Type_radioBtn, TypeHandPosture;
    EditText OpenComment;
    TextView brk_timer;

    String UserID;
    String comment;
    float speed, accuracy, easeOfUse;
    String preference, HandPosture;
    int totalSessions;
    int session;
    int runs = 1;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        ratings = new ArrayList<>();

        rb5_speed = (RatingBar) findViewById(R.id.ratingBar5);
        rb6_accuracy = (RatingBar) findViewById(R.id.ratingBar6);
        rb7_easeOfUse = (RatingBar) findViewById(R.id.ratingBar7);

        type = (RadioGroup) findViewById(R.id.type);
        postureRG = (RadioGroup) findViewById(R.id.postureRG);
        OpenComment = findViewById(R.id.commentEdit);
        submit = (Button)findViewById(R.id.rate2SubmitBTN);


        UserID = getIntent().getStringExtra("UserID");
        //totalSessions = getIntent().getIntExtra("noOfRuns",0);
        session = getIntent().getIntExtra("session",0);


        System.out.println("total runs" + totalSessions);
        if(totalSessions != 0){
            runs = totalSessions;
        }


        dbHelper = new DBHelper(this);

        System.out.println("Before" + runs);
        runs = runs + 1;
        if(runs < 3) {

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int selectedIDPreference = type.getCheckedRadioButtonId();
                    Type_radioBtn = (RadioButton) findViewById(selectedIDPreference);
                    preference = (String) Type_radioBtn.getText().toString();
                    Toast.makeText(getApplicationContext(), preference,Toast.LENGTH_SHORT).show();



                    int selectedIDHandPosture = postureRG.getCheckedRadioButtonId();
                    TypeHandPosture = (RadioButton) findViewById(selectedIDHandPosture);
                    HandPosture = (String) TypeHandPosture.getText().toString();
                    Toast.makeText(getApplicationContext(), HandPosture,Toast.LENGTH_SHORT).show();


                    speed = rb5_speed.getRating();
                    accuracy = rb6_accuracy.getRating();
                    easeOfUse = rb7_easeOfUse.getRating();
                    //String comment = OpenComment.getText().toString();
                    comment = OpenComment.getText().toString();



                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(rating.this, alertScreen.class);
                            intent.putExtra("UserID", UserID);
                            intent.putExtra("session", session);
                            startActivity(intent);
                        }
                    }, 1000 * 10);


                    createRating();
                    saveToDatabase(speed, accuracy, preference, easeOfUse, HandPosture, comment);

                    System.out.println(speed);
                    System.out.println(accuracy);
                    System.out.println(preference);
                    System.out.println(easeOfUse);
                    System.out.println(HandPosture);
                    System.out.println(comment);

                    //move the activity to background
                    moveTaskToBack(true);
                }
            });
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent=new Intent(rating.this, questionnaire.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                }
            }, 1000*60);

            createRating();
            //saveToDatabase(speed, accuracy, preference, easeOfUse, HandPosture, comment);

            //move the activity to background
            moveTaskToBack(true);
        }
    }

    /***************************** to xampp server ********************/
    private void createRating(){

        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", UserID);
        //System.out.println("data"+UserID);

        params.put("session", String.valueOf(session));
        //System.out.println("data"+session);

        params.put("speed", String.valueOf(speed));
        params.put("accuracy", String.valueOf(accuracy));
        params.put("preference", String.valueOf(preference));

        Toast.makeText(getApplicationContext(), preference,Toast.LENGTH_SHORT).show();

        params.put("easeOfUse", String.valueOf(easeOfUse));
        params.put("handPosture", String.valueOf(HandPosture));

        params.put("comment", comment);



        System.out.println("data"+speed);
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