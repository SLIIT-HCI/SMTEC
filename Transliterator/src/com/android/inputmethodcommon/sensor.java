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

    private Sensor accelerometer, gyroscope,proximity,gravity,temperature,light,humidity,pressure,magField;

    //private TextView SensorGyroscope,SensorAccelerometer,SensorLinearAcceleration,SensorProximity;

    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        getAvailableSensors();
		
    /*  SensorGyroscope = (TextView) findViewById(R.id.label_gyroscope);
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
       */
    }
	public void getAvailableSensors(){

       for(Sensor sensor : deviceSensors) {
          // Log.d("Sensor","" + sensor.getName());
           if(sensor == sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)){
               accelerometer = sensor;
               list_Sensors.add(accelerometer.getName());
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

       int sensorType = sensorEvent.sensor.getType();
    // float currentValue = sensorEvent.values[0];

        switch (sensorType) {

          /*  case Sensor.TYPE_ACCELEROMETER:
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
			*/	
			
			case Sensor.TYPE_ACCELEROMETER:
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
        super.onStart();

      /*  if (gyroscope != null) {
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
	   */	
	   
	   if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
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
        super.onStop();
        sensorManager.unregisterListener(this);
    }
	private void getAccelerometer(SensorEvent event) {
      float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];
    }
    private void getGravity(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];
    }
    private void getGyroscope(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];
    }
    private void getTemperature(SensorEvent event) {
         float value = event.values[0];
    }
    private void getPressure(SensorEvent event) {
         float value = event.values[0];
    }
    private void getLight(SensorEvent event) {
         float value = event.values[0];
    }
    private void getHumidity(SensorEvent event) {
         float value = event.values[0];
    }
    private void getProximity(SensorEvent event) {
         float value = event.values[0];
    }
    private void getMagneticField(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];
    }
}
