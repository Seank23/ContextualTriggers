package com.example.contextualtriggers.data;

import java.util.ArrayList;

public class SensorData {

    public int type;
    public ArrayList<Object> values;
    public ArrayList<Long> timestamps;

    public SensorData(int type, ArrayList<Object> value, ArrayList<Long> timestamp) {
        this.type = type;
        this.values = value;
        this.timestamps = timestamp;
    }
}
