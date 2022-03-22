package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ContextAPI extends Service implements ChangeListener{
    StepCounter step;
    int sensorType;
    double sensorValue;
    Timestamp timestamp;

    private ArrayList<SensorInterface> sensors;

    public ContextAPI() {
        sensors = new ArrayList<SensorInterface>();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i = new Intent(this, StepCounter.class);
        startService(i);

        step = new StepCounter();
        step.setChangeListener(this);


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void addSensor(SensorInterface sensor) {
        sensors.add(sensor);
    }

    public void removeSensor(SensorInterface sensor) {
        sensors.remove(sensor);
    }


    @Override
    public void onChangeHappened() {
        sensorType = step.getSensorType();
        sensorValue = step.getSensorValue();
        timestamp = step.getTimestamp();
        System.out.println("Values updated.");
    }
}
