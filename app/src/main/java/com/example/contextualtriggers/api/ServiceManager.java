package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notification.NotificationManager;
import com.example.contextualtriggers.triggers.TriggerManager;

import java.util.HashMap;
import java.util.Map;

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

    public void setData(SensorData data) {

        if(!currentData.containsKey(data.type))
            currentData.put(data.type, data);
        else if(data.timestamp > currentData.get(data.type).timestamp) {
            int prevVal = currentData.get(data.type).value;
            currentData.replace(data.type, new SensorData(data.type, prevVal + data.value, data.timestamp));
        }
        if (TriggerManager.instance.checkTriggers(currentData).containsValue(true)) {
            System.out.println("Triggered.");
            NotificationManager nm = new NotificationManager();
            nm.sendNotification(TriggerManager.instance.checkTriggers(currentData));
        }
    }

    public void handleCheckTriggers() {

        // Get data from ContextAPI

        HashMap<Integer, Boolean> triggerOutputs = TriggerManager.instance.checkTriggers(currentData);
        if(triggerOutputs.containsValue(true)) {
            System.out.println("Triggered.");
            NotificationManager.instance.sendNotification(triggerOutputs);
        }
    }
}
