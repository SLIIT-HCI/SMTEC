package com.example.wildusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.wildusers.Database.LocalDB.DBHandler;
import com.example.wildusers.Database.LocalDB.DBHelper;
import com.example.wildusers.Database.OnlineDB.Api.UserApi;
import com.example.wildusers.Database.OnlineDB.Model.User;
import com.example.wildusers.Database.OnlineDB.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StartUp extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    String UserID, Condition, RS;
    Button start;
    EditText ID, condition, rotationSequence;
    int T1, T2;
    List<User> userList;
    public static String ALARM_TO_SET = "ALRMTOSEND";
    DBHelper DB;

    // one boolean variable to check whether all the text field are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        userList = new ArrayList<>();
        start = (Button)findViewById(R.id.startBtn);
        ID = (EditText)findViewById(R.id.uIDET);
        condition = (EditText)findViewById(R.id.ConditionET);
        rotationSequence = (EditText)findViewById(R.id.rotationSequenceET);

        DB = new DBHelper(this);

        //Store user details to DB and Navigating to the text entry interface
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Passing the entering fields data to local database
                UserID = ID.getText().toString();
                System.out.println(UserID);

                Condition = condition.getText().toString();
                System.out.println(Condition);

                RS = rotationSequence.getText().toString();
                System.out.println(RS);

                createUser();
                SaveToLocalDB(UserID, Condition, RS);

                isAllFieldsChecked = validate();
                if(isAllFieldsChecked){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getApplicationContext(), ActiveTime_Selection.class);
                            startActivity(i);
                            finish();
                        }
                    }, 10);

                    //move the activity to background
                    moveTaskToBack(true);
                }

                T1 = pref.getInt("StartTime", T1);
                T2 = pref.getInt("EndTime", T2);;

                editor.putInt("countValue", 0);
                editor.commit();
                editor.putInt("dayCount", 0);
                editor.commit();
                editor.putString("UserID", UserID);
                editor.commit();
            }
        });

    }

    private boolean validate(){
        if(ID.length() == 0){
            ID.setError("Please Enter your ID");
            return false;
        }else if(condition.length() == 0){
            condition.setError("Please Enter a Condition");
            return false;
        }else if(rotationSequence.length() == 0){
            rotationSequence.setError("Please Enter a Rotation Sequence");
            return false;
        }
        else{
            return true;
        }
    }

    private void createUser(){
        String userID = ID.getText().toString().trim();
        String cond = condition.getText().toString().trim();
        String rs = rotationSequence.getText().toString().trim();

        //validating the inputs
        if (TextUtils.isEmpty(userID)) {
            ID.setError("Please enter ID");
            ID.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cond)) {
            condition.setError("Please enter the condition");
            condition.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(rs)) {
            rotationSequence.setError("Please enter rotational sequence");
            rotationSequence.requestFocus();
            return;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userID);
        params.put("condition_wild", cond);
        params.put("rotational_sequence", rs);

        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(UserApi.URL_CREATE_HERO, params, CODE_POST_REQUEST);
        request.execute();
    }

    public void SaveToLocalDB(String UserID, String Condition, String RS){
        SQLiteDatabase database = DB.getWritableDatabase();
        DB.StoreUserDetails(UserID, Condition, RS, database);
        //Toast.makeText(StartUp.this,"Data Inserted", Toast.LENGTH_SHORT).show();

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

    /********************************* mini menu **********************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mini_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.activeTime:
                Toast.makeText(this, "Currently Disabled!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Instructions:
                Intent i2 = new Intent(getApplicationContext(), Instructions.class);
                startActivity(i2);
                return true;

            case R.id.settings:
                Intent i3 = new Intent(getApplicationContext(), settings.class);
                startActivity(i3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    /******************************************* end of mini-menu ***********************************************************/

}