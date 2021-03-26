package com.example.smtec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class sensor extends AppCompatActivity implements SensorEventListener {

    ArrayList<String> list_Sensors = new ArrayList<>();
    List<Sensor> deviceSensors;
    DatabaseHelper db;

    private SensorManager sensorManager;
    private Sensor linearAccelerometer, gyroscope,proximity,gravity,temperature,light,humidity,pressure,magField;


    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_sensor);

        db = new DatabaseHelper(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        getAvailableSensors();

    }
   public void getAvailableSensors(){

       for(Sensor sensor : deviceSensors) {
          // Log.d("Sensor","" + sensor.getName());
           if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)){
               linearAccelerometer = sensor;
               list_Sensors.add(linearAccelerometer.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)){
               gravity =sensor;
               list_Sensors.add(gravity.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)){
               gyroscope = sensor;
               list_Sensors.add(gyroscope.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)){
               temperature = sensor;
               list_Sensors.add(temperature.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)){
               pressure = sensor;
               list_Sensors.add(pressure.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)){
               light = sensor;
               list_Sensors.add(light.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)){
               humidity = sensor;
               list_Sensors.add(humidity.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)){
               proximity = sensor;
               list_Sensors.add(proximity.getName());
           }
           else if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)){
               magField = sensor;
               list_Sensors.add(magField.getName());
           }
       }

    }

   @SuppressLint("ResourceType")
   @Override
   public void onSensorChanged(SensorEvent sensorEvent) {

       int sensorType = sensorEvent.sensor.getType();
       switch (sensorType) {

           case Sensor.TYPE_LINEAR_ACCELERATION:
               getAccelerometer(sensorEvent);
           case Sensor.TYPE_GRAVITY:
               getGravity(sensorEvent);
           case Sensor.TYPE_GYROSCOPE:
               getGyroscope(sensorEvent);
           case Sensor.TYPE_AMBIENT_TEMPERATURE:
               getTemperature(sensorEvent);
           case Sensor.TYPE_PRESSURE:
               getPressure(sensorEvent);
           case Sensor.TYPE_LIGHT:
               getLight(sensorEvent);
           case Sensor.TYPE_RELATIVE_HUMIDITY:
               getHumidity(sensorEvent);
           case Sensor.TYPE_PROXIMITY:
               getProximity(sensorEvent);
           case Sensor.TYPE_MAGNETIC_FIELD:
               getMagneticField(sensorEvent);
       }
   }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (linearAccelerometer != null) {
            sensorManager.registerListener(this, linearAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gravity != null) {
            sensorManager.registerListener(this, gravity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (temperature != null) {
            sensorManager.registerListener(this, temperature,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximity != null) {
            sensorManager.registerListener(this, proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (magField != null) {
            sensorManager.registerListener(this, magField,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (pressure != null) {
            sensorManager.registerListener(this, pressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (light != null) {
            sensorManager.registerListener(this, light,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (humidity != null) {
            sensorManager.registerListener(this, humidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    public float[] getAccelerometer(SensorEvent event) {
        float[] sensorData = new float[3];

        sensorData[0] = event.values[0];
        sensorData[1] = event.values[1];
        sensorData[2] = event.values[2];

        return sensorData;
    }
    public float[] getGravity(SensorEvent event) {
        float[] sensorData = new float[3];

        sensorData[0] = event.values[0];
        sensorData[1] = event.values[1];
        sensorData[2] = event.values[2];

        return sensorData;
    }
    public float[] getGyroscope(SensorEvent event) {
        float[] sensorData = new float[3];

        sensorData[0] = event.values[0];
        sensorData[1] = event.values[1];
        sensorData[2] = event.values[2];

        return sensorData;
    }
    public float getTemperature(SensorEvent event) {
         float data = event.values[0];
         return data;
    }
    public float getPressure(SensorEvent event) {
        float data = event.values[0];
        return data;
    }
    public float getLight(SensorEvent event) {
        float data = event.values[0];
        return data;
    }
    public float getHumidity(SensorEvent event) {
        float data = event.values[0];
        return data;
    }
    public float getProximity(SensorEvent event) {
        float data = event.values[0];
        return data;
    }
    public float[] getMagneticField(SensorEvent event) {
        float[] sensorData = new float[3];

        sensorData[0] = event.values[0];
        sensorData[1] = event.values[1];
        sensorData[2] = event.values[2];

        return sensorData;
    }
}
