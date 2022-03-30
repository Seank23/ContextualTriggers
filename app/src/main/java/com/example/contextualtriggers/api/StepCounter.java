package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Timestamp;

public class StepCounter extends Service implements SensorInterface, SensorEventListener {

    public static StepCounter instance;
    SensorManager mSensorManager;
    Sensor stepCounter;

    private ChangeListener listener;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        System.out.println("Hello");

        mSensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);

        return super.onStartCommand(intent, flags, startId);
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
        if (listener != null) {
            listener.onChangeHappened();
        }
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

    @Override
    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }
}

