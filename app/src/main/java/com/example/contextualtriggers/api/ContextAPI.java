package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.contextualtriggers.data.SensorData;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ContextAPI extends Service implements ChangeListener {

    public static ContextAPI instance;
    private ServiceManager serviceManager;

    private ArrayList<SensorInterface> sensors;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        serviceManager = ServiceManager.instance;
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
        int type = sensors.get(0).getSensorType();
        int value = sensors.get(0).getSensorValue();
        long timestamp = sensors.get(0).getTimestamp();
        //System.out.println("Type: " + type + " Value: " + value + " Time: " + timestamp);
        if(serviceManager != null)
            serviceManager.setData(new SensorData(type, value, timestamp));
    }
}
