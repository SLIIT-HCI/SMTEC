package com.example.labexperiment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;

public class sessionPeriod extends AppCompatActivity {

    DBHelper dbHelper;
    RadioGroup radioGroupFeedback;
    RadioButton radioButton;
    Button btn_submit,btn_results;
    String handPosture,userComment;
    EditText Comment;
    ArrayList<String> email,dateTime, stimulus, response, inputMethod;
    ArrayList<Integer> session, editDistance;

    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.activity_session_period);
        super.onCreate(savedInstanceState);

        radioGroupFeedback = (RadioGroup) findViewById(R.id.radioHandPosture);
        Comment = (EditText) findViewById(R.id.textbox);
        btn_submit = (Button) findViewById(R.id.submit);
        btn_results = (Button) findViewById(R.id.button_results);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            int selectedId = radioGroupFeedback.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            handPosture = (String) radioButton.getText().toString();
            userComment = Comment.getText().toString();
            saveComment(handPosture,userComment);

            dbHelper = new DBHelper(sessionPeriod.this);
            email = new ArrayList<>();
            session = new ArrayList<>();
            dateTime = new ArrayList<>();
            stimulus = new ArrayList<>();
            response = new ArrayList<>();
            editDistance = new ArrayList<>();
            inputMethod = new ArrayList<>();

            }
        });
        btn_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResults(view);
            }
        });
    }
    public void saveComment(String handPosture, String comment){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToCommentTable(handPosture,comment,database);
    }
    public void getResults(View view){

        StringBuilder results = new StringBuilder();
        Cursor cursor = dbHelper.readFromLocalDatabase();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else{
           while (cursor.moveToNext()){
              email.add(cursor.getString(0));
              session.add(cursor.getInt(1));
              dateTime.add(cursor.getString(2));
              stimulus.add(cursor.getString(3));
              response.add(cursor.getString(4));
              editDistance.add(cursor.getInt(5));
              inputMethod.add(cursor.getString(6));
           }try {
                // saving the file into device
                FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                for(int i=0;i<cursor.getCount();i++){

                   out.write((email.get(i)).getBytes());
                   out.write((session.get(i).toString()).getBytes());
                   out.write((dateTime.get(i)).getBytes());
                   out.write((stimulus.get(i)).getBytes());
                   out.write((response.get(i)).getBytes());
                   out.write((editDistance.get(i).toString()).getBytes());
                   out.write((inputMethod.get(i)).getBytes());
                }
                out.close();
                //exporting data
                Context context = getApplicationContext();
                File fileLocation = new File(getFilesDir(), "data.csv");
                Uri path = FileProvider.getUriForFile(context,"com.example.labexperiment.fileprovider",fileLocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent,"Download file"));
            }catch (Exception ex){
               ex.printStackTrace();
            }
        }
    }
}

