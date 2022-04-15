package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.database.stepsEntity;
import com.example.contextualtriggers.database.stepsRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContextAPI extends Service implements ChangeListener {

    public static ContextAPI instance;
    private stepsRepository sr;
    private ArrayList<SensorInterface> sensors;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sensors = new ArrayList<>();
        sr = new stepsRepository(getApplication());
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

    public ArrayList<Integer> getSensorTypes() {
        ArrayList<Integer> types = new ArrayList<>();
        for(SensorInterface sensor : sensors)
            types.add(sensor.getSensorType());
        return types;
    }

    public Long getLatestTimestamp() {
        stepsRepository repository = new stepsRepository(getApplication());
        stepsEntity entity = repository.getLatestStepCount();
        return entity.getTimestamp();
    }

    public double getLatestStepCount() {
        stepsRepository repository = new stepsRepository(getApplication());
        stepsEntity entity = repository.getLatestStepCount();
        return entity.getStepCount();
    }

    public List<stepsEntity> getStepsTable() {
        stepsRepository rep = new stepsRepository(getApplication());
        return rep.getStepsTable();
    }

    public HashMap<Integer, SensorData> getData() {

        List<stepsEntity> steps = getStepsTable();
        HashMap<Integer, SensorData> allData = new HashMap<>();

        // Steps data
        SensorData stepSensorData = new SensorData(0, new ArrayList<>(), new ArrayList<>());
        for(stepsEntity step : steps) {
            stepSensorData.values.add(step.getStepCount());
            stepSensorData.timestamps.add(step.getTimestamp());
        }

        allData.put(0, stepSensorData);
        return allData;
    }

    @Override
    public void onChangeHappened(int type) {

        int value = sensors.get(type).getSensorValue();
        long timestamp = sensors.get(type).getTimestamp();

        switch (type) {
            case 0:
                sr.insert(new stepsEntity(value, timestamp));
                break;
        }
    }
}
