package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Timestamp;

public class StepCounter extends Service implements SensorInterface, SensorEventListener {

    SensorManager mSensorManager;
    Sensor stepCounter;

    public StepCounter() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

       

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mSensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        mSensorManager.unregisterListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.out.println("Sensor changed.");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public int getSensorType() {
        return 0;
    }

    @Override
    public double getSensorValue() {
        return 0;
    }

    @Override
    public Timestamp getTimestamp() {
        return null;
    }

    @Override
    public void setSensorType(int sensorType) {

    }

    @Override
    public void setSensorValue(double sensorValue) {

    }

    @Override
    public void setTimestamp(Timestamp timestamp) {

    }
}
