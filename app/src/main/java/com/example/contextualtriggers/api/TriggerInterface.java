package com.example.contextualtriggers.api;

import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.api.NotificationInterface;

import java.util.HashMap;

public interface TriggerInterface {

    int getId();

    NotificationInterface getNotification();

    String getSensorsRequired();

    boolean checkTrigger(HashMap<Integer, SensorData> data);
}
