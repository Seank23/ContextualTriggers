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
    private ServiceManager serviceManager;

    private stepsRepository sr;

    private ArrayList<SensorInterface> sensors;

    int latestStepValue = 0;
    String latestTimestamp = "";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        serviceManager = ServiceManager.instance;
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

    public String getLatestTimestamp() {
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

        SensorData stepSensorData = new SensorData(0, new ArrayList<>(), new ArrayList<>());
        for(stepsEntity step : steps) {
            stepSensorData.values.add(step.getStepCount());
            stepSensorData.timestamps.add(Timestamp.valueOf(step.getTimestamp()).getTime());
        }

        allData.put(0, stepSensorData);
        return allData;
    }

    @Override
    public void onChangeHappened() {
        int type = sensors.get(0).getSensorType();
        int value = sensors.get(0).getSensorValue();
        long timestamp = sensors.get(0).getTimestamp();
        //System.out.println("Type: " + type + " Value: " + value + " Time: " + timestamp);

        //Gets most recent entry - compared timestamps and it outputs the values just inserted above.
        stepsRepository rep = new stepsRepository(getApplication());
        stepsEntity e = rep.getLatestStepCount();
        //System.out.println("OTHER: "+e.getTimestamp());
        int oldVal = e.getStepCount();
        int diff = value - oldVal;


        if (diff > -4) {
            sr.insert(new stepsEntity(value,new Timestamp(System.currentTimeMillis()).getTime()));
        }



    }
}
