package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.api.NotificationInterface;
import com.example.contextualtriggers.api.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.GoodWeatherReachTargetNotification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GoodWeatherReachTargetTrigger implements TriggerInterface {

    private ArrayList<String> args = new ArrayList<>();
    @Override
    public int getId() {
        return 3;
    }

    @Override
    public NotificationInterface getNotification() {
        return new GoodWeatherReachTargetNotification(getArgs());
    }

    @Override
    public String getSensorsRequired() {
        return "01";
    }

    @Override
    public String[] getArgs() {
        return args.toArray(new String[args.size()]);
    }

    @Override
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {

        SensorData stepsData = data.get(0);
        SensorData weatherData = data.get(1);
        if(stepsData == null || weatherData == null)
            return false;

        List<String> goodWeather = Arrays.asList("Clear", "Clouds");
        int stepTarget = 10000;
        args.add(0, Integer.toString(stepTarget));
        long timeThreshold = getSeconds(new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())) * 1000; // Time in today so far

        long currentTime = System.currentTimeMillis();
        int totalSteps = 0;
        int i = stepsData.timestamps.size() - 1;
        while(i >= 0 && stepsData.timestamps.get(i) > currentTime - timeThreshold) {
            totalSteps += (int)stepsData.values.get(i);
            i--;
        }

        if(goodWeather.contains((String) weatherData.values.get(0)) && totalSteps < stepTarget)
            return true;

        return false;
    }

    private int getSeconds(String timeStr) {
        String[] split = timeStr.split(":");
        return Integer.parseInt(split[2]) + Integer.parseInt(split[1]) * 60 + Integer.parseInt(split[0]) * 3600;
    }
}
