package com.example.contextualtriggers;

import com.example.contextualtriggers.data.SensorData;

public class Utils {

    public static int getStepCountOverTime(long timeThreshold, SensorData stepsData) {

        // Get steps difference between most recent recording and closest recording to the time threshold
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
        return (int)stepsData.values.get(0) - (int)stepsData.values.get(closestIndex);
    }

    public static int getSeconds(String timeStr) {
        String[] split = timeStr.split(":");
        return Integer.parseInt(split[2]) + Integer.parseInt(split[1]) * 60 + Integer.parseInt(split[0]) * 3600;
    }
}
