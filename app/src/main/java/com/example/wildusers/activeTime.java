package com.example.wildusers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wildusers.Database.LocalDB.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.format.DateTimeFormatter;

import me.bendik.simplerangeview.SimpleRangeView;

public class activeTime extends AppCompatActivity {

    SimpleRangeView rangeView;
    TextView timeRange;
    FloatingActionButton submit;

    String UserID;
    String formatTime;
    String formatDateTime2;
    //DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.S");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_time);

        rangeView = findViewById(R.id.rangeSlider);
        timeRange = findViewById(R.id.timeRange);
        submit = (FloatingActionButton) findViewById(R.id.submitTime);

        UserID = getIntent().getStringExtra("UserID");

        rangeView.setOnChangeRangeListener(new SimpleRangeView.OnChangeRangeListener() {
            @Override
            public void onRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i, int i1) {
                timeRange.setText(String.valueOf(i)+" - "+String.valueOf(i1));
            }
        });

        rangeView.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                timeRange.setText(String.valueOf(i));
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                timeRange.setText(String.valueOf(i));
            }
        });

        rangeView.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return String.valueOf(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatTime = timeRange.getText().toString();

                SaveTimeToDB(formatTime);
                Toast.makeText(activeTime.this, "Thank You Your Time Selection is Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        //formatDateTime2 = formatTime.format(String.valueOf(formatDate));
    }

    public void SaveTimeToDB(String Time){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.StoreActiveTime(UserID, Time, database);
    }
}