package com.example.contextualtriggers.triggers;

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
        long currentTime = System.currentTimeMillis();

        long targetTime = currentTime - timeThreshold;
        long closest = Math.abs(stepsData.timestamps.get(0) - targetTime);
        int closestIndex = 0;
        for(int i = 1; i < stepsData.timestamps.size(); i++){
            long distance = Math.abs(stepsData.timestamps.get(i) - targetTime);
            if(distance < closest){
                closestIndex = i;
                closest = distance;
            }
        }
        // Get steps difference between most recent recording and closest recording to time threshold
        int totalSteps = (int)stepsData.values.get(0) - (int)stepsData.values.get(closestIndex);

        if(totalSteps < 100)
            return true;

        return false;
    }
}
