package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ContextAPI extends Service implements ChangeListener {

    public static ContextAPI instance;
    int sensorType;
    double sensorValue;
    Timestamp timestamp;

    private ArrayList<SensorInterface> sensors;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sensors = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void addSensor(SensorInterface sensor) {
        sensor.setChangeListener(this);
        sensors.add(sensor);
    }

    public void removeSensor(SensorInterface sensor) {
        sensors.remove(sensor);
    }

    @Override
    public void onChangeHappened() {
        sensorType = sensors.get(0).getSensorType();
        sensorValue = sensors.get(0).getSensorValue();
        timestamp = sensors.get(0).getTimestamp();
        System.out.println("Type: " + sensorType + " Value: " + sensorValue + " Time: " + timestamp);
    }
}
