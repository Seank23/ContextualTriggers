package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.api.NotificationInterface;
import com.example.contextualtriggers.api.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.GoodWeatherNotification;

import java.util.ArrayList;
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
        return "1";
    }

    @Override
    public String[] getArgs() {
        return new String[0];
    }

    @Override
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {

        List<String> goodWeather = Arrays.asList("Clear", "Clouds");
        SensorData weatherData = data.get(1);

        if(weatherData != null) {
            if (goodWeather.contains((String) weatherData.values.get(0)))
                return true;
        }
        return false;
    }
}
