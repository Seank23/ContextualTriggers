package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.framework.NotificationInterface;
import com.example.contextualtriggers.framework.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.GoodWeatherNotification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GoodWeatherTrigger implements TriggerInterface {
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public NotificationInterface getNotification() {
        return new GoodWeatherNotification();
    }

    @Override
    public String getSensorsRequired() {
        return "01";
    }

    @Override
    public String[] getArgs() {
        return new String[0];
    }

    @Override
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {

        SensorData stepsData = data.get(0);
        SensorData weatherData = data.get(1);
        if(stepsData == null || weatherData == null)
            return false;
        if(weatherData.values.isEmpty())
            return false;

        List<String> goodWeather = Arrays.asList("Clear", "Clouds");
        int stepTarget = 10000;
        int latestStepCount = (int)stepsData.values.get(0);

        if (latestStepCount > stepTarget && goodWeather.contains((String) weatherData.values.get(0)))
            return true;

        return false;
    }
}
