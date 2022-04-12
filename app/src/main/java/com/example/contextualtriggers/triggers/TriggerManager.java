package com.example.contextualtriggers.triggers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.SensorInterface;
import com.example.contextualtriggers.data.SensorData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TriggerManager extends Service {

    public static TriggerManager instance;
    private ArrayList<TriggerInterface> triggers;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        triggers = new ArrayList<>();
    }

    public void addTrigger(TriggerInterface trigger) {
        triggers.add(trigger);
    }

    public void removeTrigger(TriggerInterface trigger) {
        triggers.remove(trigger);
    }

    public HashMap<Integer, Boolean> checkTriggers(HashMap<Integer, SensorData> dataHashMap) {

        HashMap<Integer, Boolean> triggerOutput = new HashMap<>();

        for(TriggerInterface trigger : triggers) {

            HashMap<Integer, SensorData> triggerData = getTriggerData(trigger.getSensorsRequired(), dataHashMap);
            boolean output = trigger.checkTrigger(triggerData);
            triggerOutput.put(trigger.getId(), output);
        }
        return triggerOutput;
    }

    private HashMap<Integer, SensorData> getTriggerData(String sensorTypes, HashMap<Integer, SensorData> inputData) {

        HashMap<Integer, SensorData> outputData = new HashMap<>();
        Integer[] activeSensors = ContextAPI.instance.getSensorTypes();
        for (int type : activeSensors) {
            if (sensorTypes.contains(Integer.toString(type)))
                outputData.put(type, inputData.get(type));
        }
        return outputData;
    }
}
