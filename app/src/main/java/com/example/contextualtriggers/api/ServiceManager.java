package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.example.contextualtriggers.data.SensorData;

import java.util.HashMap;

public class ServiceManager extends Service {

    public static ServiceManager instance;
    private HashMap<Integer, SensorData> currentData;

    public ServiceManager() {
        currentData = new HashMap<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void handleCheckTriggers() {

        HashMap<Integer, SensorData> sensorData = ContextAPI.instance.getData();

        HashMap<Integer, Boolean> triggerOutputs = TriggerManager.instance.checkTriggers(sensorData);
        if(triggerOutputs.containsValue(true)) {
            System.out.println("Triggered.");
            HashMap<Integer, NotificationInterface> triggerNotifications = TriggerManager.instance.getTriggerNotifications(triggerOutputs);
            NotificationManager.instance.sendNotification(triggerNotifications);
        }
    }
}
