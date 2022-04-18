package com.example.contextualtriggers.sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.contextualtriggers.framework.ChangeListener;
import com.example.contextualtriggers.framework.SensorInterface;

public class StepCounter extends Service implements SensorInterface, SensorEventListener {

    public static StepCounter instance;
    SensorManager mSensorManager;
    Sensor stepCounter;

    long timestamp = 0;
    int steps = 0;

    private ChangeListener listener;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
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

        System.out.println(sensorEvent.values[0]);
        if (sensorEvent.values[0]==0.0) {
            return;
        }
        steps = (int)sensorEvent.values[0];
        timestamp = System.currentTimeMillis();
        if (listener != null) {
            listener.onChangeHappened(getSensorType());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    @Override
    public int getSensorType() {
        return 0;
    }

    @Override
    public Object getSensorValue() {
        return (Object)steps;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }
}

