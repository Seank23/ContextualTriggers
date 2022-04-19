package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.Utils;
import com.example.contextualtriggers.framework.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.InactivityNotification;
import com.example.contextualtriggers.framework.NotificationInterface;

import java.util.HashMap;

public class StepTrigger implements TriggerInterface {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public NotificationInterface getNotification() {
        return new InactivityNotification();
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
        int totalSteps = Utils.getStepCountOverTime(timeThreshold, stepsData);

        if(totalSteps < 100)
            return true;

        return false;
    }
}
