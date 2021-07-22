package com.example.wildusers;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AccelerometerDetection extends AppCompatActivity implements SensorEventListener {

    private TextView x, y, z;
    private Sensor sensor;
    private SensorManager SM;
    public Button storeSensor;


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

        //assigning text views
        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.y);
        z = (TextView) findViewById(R.id.z);

        storeSensor = (Button) findViewById(R.id.storeBtn);
        storeSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //impl storing to db part
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x.setText("" + event.values[0]);
        y.setText("" + event.values[1]);
        z.setText("" + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}