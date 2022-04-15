package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notification.NotificationInterface;

import java.util.HashMap;

public interface TriggerInterface {

    int getId();

    NotificationInterface getNotification();

    String getSensorsRequired();

    boolean checkTrigger(HashMap<Integer, SensorData> data);
}
