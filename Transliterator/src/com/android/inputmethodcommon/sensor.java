package com.example.smtec;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class sensor extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor accelerometer;
    private Sensor gyroscope;

    private TextView SensorGyroscope;
    private TextView SensorAccelerometer;

    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        SensorGyroscope = (TextView) findViewById(R.id.label_gyroscope);
        SensorAccelerometer = (TextView) findViewById(R.id.label_accelerometer);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (gyroscope == null) {
            SensorGyroscope.setText(sensor_error);
        }
        if (accelerometer == null) {
            SensorAccelerometer.setText(sensor_error);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

       // int sensorType = event.sensor.getType();
      //  float currentValue = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onStart() {
        super.onStart();

        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
