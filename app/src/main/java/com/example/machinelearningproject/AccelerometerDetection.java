package com.example.machinelearningproject;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.machinelearningproject.LocalDatabase.Database;


public class AccelerometerDetection extends AppCompatActivity implements SensorEventListener {

    private TextView x, y, z, xOrientation, yOrientation, zOrientation;
    private Sensor sensor;
    private Sensor gyroscopeSensor;
    private SensorManager SM;
    private SensorEventListener gyroscopeEventListener;
    public Button storeSleep, storeStand, storeWalk;
    String x1, y1, z1;
    String x2, y2, z2;
    String x3, y3, z3;
    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_detection);

        //creating sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //accelerometer sensor
        sensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //register sensor listener
        SM.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //gyroscope
        gyroscopeSensor = SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscopeSensor == null){
            Toast.makeText(this, "Gyroscope is not available in this Device!", Toast.LENGTH_SHORT).show();
            finish();
        }
        gyroscopeEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[2] > 0.5f){
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }else if(sensorEvent.values[2] < -0.5f){
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        //assigning text views
        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.y);
        z = (TextView) findViewById(R.id.z);
        xOrientation = (TextView) findViewById(R.id.ox);
        yOrientation = (TextView) findViewById(R.id.oy);
        zOrientation = (TextView) findViewById(R.id.oz);

        DB = new Database(this);

        storeSleep = (Button) findViewById(R.id.sleepBtn);
        storeSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //impl storing to db part
                x3 = "X : " + x.getText().toString();
                y3 = "Y : "+ y.getText().toString();
                z3 = "Z : " + z.getText().toString();
                saveSleepSensorData(x3,y3,z3);

                Toast.makeText(getApplicationContext(), "You are Sleeping", Toast.LENGTH_SHORT).show();
            }
        });

        storeWalk = (Button) findViewById(R.id.walkBtn);
        storeWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //impl storing to db part
                x2 = "X : " + x.getText().toString();
                y2 = "Y : "+ y.getText().toString();
                z2 = "Z : " + z.getText().toString();
                saveWalkSensorData(x2,y2,z2);

                Toast.makeText(getApplicationContext(), "You are Walking", Toast.LENGTH_SHORT).show();
            }
        });

        storeStand = (Button) findViewById(R.id.standBtn);
        storeStand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //impl storing to db part
                x1 = "X : " + x.getText().toString();
                y1 = "Y : "+ y.getText().toString();
                z1 = "Z : " + z.getText().toString();
                saveStandSensorData(x1,y1,z1);

                Toast.makeText(getApplicationContext(), "You are Standing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveStandSensorData(String x1, String y1, String z1){
        SQLiteDatabase database = DB.getWritableDatabase();
        DB.saveStandSensorDataToTable(x1, y1, z1, database);
    }

    public void saveWalkSensorData(String x2, String y2, String z2){
        SQLiteDatabase database = DB.getWritableDatabase();
        DB.saveWalkSensorDataToTable(x2, y2, z2, database);
    }

    public void saveSleepSensorData(String x3, String y3, String z3){
        SQLiteDatabase database = DB.getWritableDatabase();
        DB.saveSleepSensorDataToTable(x3, y3, z3, database);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this) {
            switch (event.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    x.setText("" + event.values[0]);
                    y.setText("" + event.values[1]);
                    z.setText("" + event.values[2]);
                    break;
                case Sensor.TYPE_ORIENTATION:
                    xOrientation.setText(""+ event.values[0]);
                    yOrientation.setText(""+ event.values[1]);
                    zOrientation.setText(""+ event.values[2]);
                    break;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SM.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SM.unregisterListener(gyroscopeEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SM.SENSOR_DELAY_GAME);
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_ORIENTATION), SM.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        SM.unregisterListener(this, SM.getDefaultSensor(Sensor.TYPE_ORIENTATION));
    }
}