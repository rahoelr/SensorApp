package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorHumidity;
    private Sensor mSensorPressure;
    private Sensor mSensorAmbient;
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorHumidity;
    private TextView mTextSensorPressure;
    private TextView mTextSensorAmbient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//
//        StringBuilder sensorText = new StringBuilder();
//
//        for(Sensor currentSensor : sensorList){
//            sensorText.append(currentSensor.getName()).append(
//                    System.getProperty("line.separator")
//            );
//        }
//        TextView sensorTextView = (TextView) findViewById(R.id.sensor_list);
//        sensorTextView.setText(sensorText);

        mTextSensorLight = (TextView) findViewById(R.id.label_light);
        mTextSensorProximity = (TextView) findViewById(R.id.label_proximity);
        mTextSensorAmbient = (TextView) findViewById(R.id.label_ambient);
        mTextSensorHumidity = (TextView) findViewById(R.id.label_humidity);
        mTextSensorPressure = (TextView) findViewById(R.id.label_pressure);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorAmbient = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        String sensor_error = "No Sensor";

        if(mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }

        if(mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }

        if(mSensorAmbient == null){
            mTextSensorAmbient.setText(sensor_error);
        }
        if(mSensorHumidity == null){
            mTextSensorHumidity.setText(sensor_error);
        }
        if(mSensorPressure == null){
            mTextSensorPressure.setText(sensor_error);
        }

    }

    protected void onStart(){
        super.onStart();
        if(mSensorProximity != null){
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(mSensorAmbient != null){
            mSensorManager.registerListener(this, mSensorAmbient,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorPressure != null){
            mSensorManager.registerListener(this, mSensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorHumidity != null){
            mSensorManager.registerListener(this, mSensorHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(
                        R.string.label_light, currentValue
                ));
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(
                        R.string.label_proximity, currentValue
                ));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorAmbient.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(String.format("Humidity sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    private void changeBackgroundColor(float currentValue){
        ConstraintLayout layout = findViewById(R.id.constraint_layout);
        if(currentValue <= 40000 && currentValue >= 20000) layout.setBackgroundColor(Color.RED);
        else if (currentValue < 20000 && currentValue >= 10) layout.setBackgroundColor(Color.YELLOW); {

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}