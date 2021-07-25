package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.wildusers.Database.LocalDB.DBHelper;
import com.example.wildusers.Database.OnlineDB.Api.QuestionnaireApi;
import com.example.wildusers.Database.OnlineDB.Model.User;
import com.example.wildusers.Database.OnlineDB.Model.W_Questionnaire;
import com.example.wildusers.Database.OnlineDB.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class questionnaire extends AppCompatActivity {

    DBHelper dbHelper;
    RatingBar rb_move, rb_walk, rb_busy, rb_tired;
    Button SubmitQuestionnaire;

    String UserID;
    float move, walk, busy, tired;


    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    List<W_Questionnaire> questionnaires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        questionnaires = new ArrayList<>();

        rb_move = (RatingBar) findViewById(R.id.ratingBar);
        rb_walk = (RatingBar) findViewById(R.id.ratingBar2);
        rb_busy = (RatingBar) findViewById(R.id.ratingBar3);
        rb_tired = (RatingBar) findViewById(R.id.ratingBar4);

        UserID = getIntent().getStringExtra("UserID");

        dbHelper = new DBHelper(this);

        SubmitQuestionnaire = (Button) findViewById(R.id.QsubmitBtn);
        SubmitQuestionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move = rb_move.getRating();
                walk = rb_walk.getRating();
                busy = rb_busy.getRating();
                tired = rb_tired.getRating();

                createQuestionnaire();
                saveToDatabase(move, walk,busy,tired);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();

                        //destroy the app completely
                        System.exit(0);
                    }
                }, 1000*60);

            }
        });
    }


    private void createQuestionnaire(){
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", UserID);
        params.put("move", String.valueOf(move));
        params.put("walk", String.valueOf(walk));
        params.put("busy", String.valueOf(busy));
        params.put("tired", String.valueOf(tired));


        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(QuestionnaireApi.URL_CREATE_QUESTIONNAIRE, params, CODE_POST_REQUEST);
        request.execute();
    }




    public void saveToDatabase(float move,float walk, float busy, float tired){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToQuestionnaireTable(UserID, move, walk, busy,tired, database);
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