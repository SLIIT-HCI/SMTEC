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

    private Sensor accelerometer, gyroscope,linearAcceleration,proximity;

    private TextView SensorGyroscope,SensorAccelerometer,SensorLinearAcceleration,SensorProximity;

    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        SensorGyroscope = (TextView) findViewById(R.id.label_gyroscope);
        SensorAccelerometer = (TextView) findViewById(R.id.label_accelerometer);
		SensorLinearAcceleration = (TextView)findViewById(R.id.label_linear_acceleration);
        SensorProximity = (TextView)findViewById(R.id.label_proximity);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		linearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (gyroscope == null) {
            SensorGyroscope.setText(sensor_error);
        }
        if (accelerometer == null) {
            SensorAccelerometer.setText(sensor_error);
        }
		if (linearAcceleration == null) {
            SensorLinearAcceleration.setText(sensor_error);
        }
        if (proximity == null) {
            SensorProximity.setText(sensor_error);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

       int sensorType = sensorEvent.sensor.getType();
       float currentValue = sensorEvent.values[0];

        switch (sensorType) {

            case Sensor.TYPE_ACCELEROMETER:
                SensorAccelerometer.setText(getResources().getString(R.id.label_accelerometer, currentValue));
                break;
            case Sensor.TYPE_GYROSCOPE:
                SensorGyroscope.setText(getResources().getString(R.id.label_gyroscope, currentValue));
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                SensorLinearAcceleration.setText(getResources().getString(R.id.label_linear_acceleration, currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                SensorProximity.setText(getResources().getString(R.id.label_proximity, currentValue));
                break;
        }
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
		if (linearAcceleration != null) {
            sensorManager.registerListener(this, linearAcceleration,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximity != null) {
            sensorManager.registerListener(this, proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
