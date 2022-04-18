package com.example.contextualtriggers.framework;

import com.example.contextualtriggers.data.SensorData;

import java.util.HashMap;

public interface TriggerInterface {

    int getId();

    NotificationInterface getNotification();

    String getSensorsRequired();

    String[] getArgs();

    boolean checkTrigger(HashMap<Integer, SensorData> data);
}
