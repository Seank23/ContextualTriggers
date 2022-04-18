package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.framework.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.MyNotification;
import com.example.contextualtriggers.framework.NotificationInterface;

import java.util.HashMap;

public class StepTrigger implements TriggerInterface {


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public NotificationInterface getNotification() {
        return new MyNotification();
    }

    @Override
    public String getSensorsRequired() { return "0"; }

    @Override
    public String[] getArgs() {
        return new String[0];
    }

    @Override
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {

        SensorData stepsData = data.get(0);
        if(stepsData == null)
            return false;
        if(stepsData.values.isEmpty())
            return false;

        long timeThreshold = 3600000; // 1 hour
        long currentTime = System.currentTimeMillis();
        int totalSteps = 0;
        int i = stepsData.timestamps.size() - 1;
        while(i >= 0 && stepsData.timestamps.get(i) > currentTime - timeThreshold) {
            totalSteps += (int)stepsData.values.get(i);
            i--;
        }

        if(totalSteps < 100) {
            System.out.println("Trigger activated: " + totalSteps + " steps");
            return true;
        }
        else {
            System.out.println("Trigger inactive: " + totalSteps + " steps");
            return false;
        }
    }
}
