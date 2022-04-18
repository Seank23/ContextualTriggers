package com.example.contextualtriggers.framework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.contextualtriggers.data.SensorData;

import java.util.HashMap;
import java.util.HashSet;

public class TriggerManager extends Service {

    public static TriggerManager instance;
    private HashMap<Integer, TriggerInterface> triggers;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        triggers = new HashMap<>();
    }

    public void addTrigger(TriggerInterface trigger) {
        triggers.put(trigger.getId(), trigger);
    }

    public void removeTrigger(TriggerInterface trigger) {
        triggers.remove(trigger);
    }

    public HashMap<Integer, Boolean> checkTriggers(HashMap<Integer, SensorData> dataHashMap) {

        HashMap<Integer, Boolean> triggerOutput = new HashMap<>();

        for(TriggerInterface trigger : triggers.values()) {

            HashMap<Integer, SensorData> triggerData = getTriggerData(trigger.getSensorsRequired(), dataHashMap);
            boolean output = trigger.checkTrigger(triggerData);
            triggerOutput.put(trigger.getId(), output);
        }
        return triggerOutput;
    }

    public HashMap<Integer, NotificationInterface> getTriggerNotifications(HashMap<Integer, Boolean> triggerStates) {

        HashMap<Integer, NotificationInterface> triggerNotifications = new HashMap<>();
        for(int key : triggerStates.keySet()) {
            if(triggerStates.get(key))
                triggerNotifications.put(key, triggers.get(key).getNotification());
        }
        return  triggerNotifications;
    }

    private HashMap<Integer, SensorData> getTriggerData(String sensorTypes, HashMap<Integer, SensorData> inputData) {

        HashMap<Integer, SensorData> outputData = new HashMap<>();
        HashSet<Integer> activeSensors = ContextAPI.instance.getDataBindings();
        for (int type : activeSensors) {
            if (sensorTypes.contains(Integer.toString(type)))
                outputData.put(type, inputData.get(type));
        }
        return outputData;
    }
}
