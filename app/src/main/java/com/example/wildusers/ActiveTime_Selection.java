package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wildusers.Database.LocalDB.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ActiveTime_Selection extends AppCompatActivity {


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    TextView timeStart, timeEnd;
    TimePicker simpleTimePicker, simpleTimePicker2;
    FloatingActionButton submit;

    String startTime, endTime;
    int T1, T2;
    Time dateStart, dateEnd;
    String formatStartTime1, formatEndTime2;
    String UserID;
    int StarthourOfDay,EndhourOfDay;
    int StartMinute, endMinute;
    int S = 10;
    int CurrentFormatedTime;
    int CurrentFormatedMinute;
    double timestamp;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
    DateTimeFormatter mFormatter = DateTimeFormatter.ofPattern("mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_time__selection);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        UserID = pref.getString("UserID","");


        timeStart = (TextView) findViewById(R.id.timeStart);
        timeEnd = (TextView) findViewById(R.id.timeEnd);
        simpleTimePicker = (TimePicker)findViewById(R.id.simpleTimePicker);
        simpleTimePicker2 = (TimePicker)findViewById(R.id.simpleTimePicker2);
        submit = (FloatingActionButton) findViewById(R.id.submitTime);



        // This method returns true if its 24 hour mode or false if AM/PM mode is set.

        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                StarthourOfDay = simpleTimePicker.getHour();
                StartMinute = simpleTimePicker.getMinute();
                simpleTimePicker.setIs24HourView(false);

                Date date = new Date();
                //This method returns the time in millis

                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), StarthourOfDay + "  " + StartMinute, Toast.LENGTH_SHORT).show();
                timeStart.setText(StarthourOfDay + " : " + StartMinute + ": 00"); // set the current time in text view
            }
        });

        simpleTimePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                EndhourOfDay = simpleTimePicker2.getHour();
                endMinute = simpleTimePicker2.getMinute();
                simpleTimePicker2.setIs24HourView(false);

                Date date = new Date();
                //This method returns the time in millis

                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), EndhourOfDay + "  " + endMinute, Toast.LENGTH_SHORT).show();
                timeEnd.setText(EndhourOfDay + " : " + endMinute+ ": 00"); // set the current time in text view
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startTime = timeStart.getText().toString();
                endTime = timeEnd.getText().toString();
                saveActiveTimeToDB(startTime,endTime );

                editor.putInt("StartTime", StarthourOfDay);
                editor.commit();
                System.out.println("start time "+ StarthourOfDay);


                editor.putInt("EndTime", EndhourOfDay);
                editor.commit();
                System.out.println("end time "+ EndhourOfDay);


                System.out.println("T1 "+ T1);

                /*********************** S impl **********************/
                LocalTime now = LocalTime.now();
                CurrentFormatedTime = Integer.parseInt(formatter.format(now));
                CurrentFormatedMinute = Integer.parseInt(mFormatter.format(now));

                System.out.println("Time now  - "+ CurrentFormatedTime);
                System.out.println("Minutes now  - "+ CurrentFormatedMinute);


                double delta = 0.1;
                long ActiveDurationInHours = EndhourOfDay - StarthourOfDay;
                float ActiveWindowinHours = ActiveDurationInHours / 2.0f;

                double[] NST = new double[S];

                for(int i = 0; i < S; i++) {
                    NST[i] = NST[0] + (ActiveWindowinHours * i) + random(ActiveWindowinHours * delta);
                }

                for(double timestamp: NST) {
                    System.out.println("Time Stamps "+convert(T1, timestamp));
                }

                convertAndSave(NST);

                int hourToStart = pref.getInt("HoursRound0",0);
                int minutesToStart = pref.getInt("MinutesRound0",0);


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourToStart);
                calendar.set(Calendar.MINUTE, minutesToStart);
                calendar.set(Calendar.SECOND, 0);

                Alarm.startAlarm(calendar,getApplicationContext());

                //move to background
                moveTaskToBack(true);

            }
        });
    }

    public void saveActiveTimeToDB(String start, String end){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.StoreActiveTimeSelector(UserID, start, end, database);
    }


    /****************************** S impl  *******************************************/
    public static double random(double range) {
        double result = Math.random() * range;
        boolean sign = (Math.random() > 0.5);

        if(sign)
            return result;
        else
            return -result;
    }

    public static String convert(int T1, double T) {
        int h = (int)T;
        int m = (int)((T%1)*60);

        return String.format("%02d:%02d", (T1+h), m);
    }

    public void convertAndSave(double[] timeArray) {

        int count = 0;
        for(double timestamp: timeArray) {
            int hoursValue = (int)timestamp;
            int minuteValues = (int)((timestamp%1)*60);

            int hourTime = StarthourOfDay + hoursValue;
            int minuteTime = StartMinute + minuteValues;

            String keyHours = "HoursRound" + count;
            String keyMinutes = "MinutesRound" + count;
            count++;

            editor.putInt(keyHours, abs(hourTime));
            editor.commit();
            editor.putInt(keyMinutes, abs(minuteTime));
            editor.commit();
        }

    }

}