package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wildusers.Database.LocalDB.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ActiveTime_Selection extends AppCompatActivity {

    TextView timeStart, timeEnd;
    TimePicker simpleTimePicker, simpleTimePicker2;
    FloatingActionButton submit;

    String startTime, endTime;
    String formatStartTime1, formatEndTime1;
    String UserID;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("HH:mm:ss.S");


    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_time__selection);

        UserID = getIntent().getStringExtra("UserID");


        timeStart = (TextView) findViewById(R.id.timeStart);
        timeEnd = (TextView) findViewById(R.id.timeEnd);
        simpleTimePicker = (TimePicker)findViewById(R.id.simpleTimePicker);
        simpleTimePicker2 = (TimePicker)findViewById(R.id.simpleTimePicker2);
        submit = (FloatingActionButton) findViewById(R.id.submitTime);

        //Boolean mode=simpleTimePicker.is24HourView(); // This method returns true if its 24 hour mode or false if AM/PM mode is set.

        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                int hourOfDay = simpleTimePicker.getHour();
                int minute = simpleTimePicker.getMinute();

                simpleTimePicker.setIs24HourView(false);

                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                timeStart.setText(hourOfDay + " : " + minute); // set the current time in text view
            }
        });

        simpleTimePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                int hourOfDay = simpleTimePicker2.getHour();
                int minute = simpleTimePicker2.getMinute();
                simpleTimePicker2.setIs24HourView(false);

                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                timeEnd.setText(hourOfDay + " : " + minute); // set the current time in text view
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTime = timeStart.getText().toString();
                //formatStartTime1 = startTime.format(formatDate);

                endTime = timeEnd.getText().toString();
                saveActiveTimeToDB(startTime,endTime );
            }
        });
    }

    public void saveActiveTimeToDB(String start, String end){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.StoreActiveTimeSelector(UserID, start, end, database);
    }
}