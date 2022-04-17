package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.api.NotificationInterface;
import com.example.contextualtriggers.api.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.GoodWeatherNotification;

import java.util.HashMap;

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
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {
        return false;
    }
}
