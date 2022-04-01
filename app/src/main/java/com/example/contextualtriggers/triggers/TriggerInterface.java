package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.data.SensorData;

import java.util.HashMap;

public interface TriggerInterface {

    int getId();
    boolean checkTrigger(HashMap<Integer, SensorData> data);
}
