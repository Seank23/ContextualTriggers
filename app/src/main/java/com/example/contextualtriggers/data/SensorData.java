package com.example.contextualtriggers.data;

public class SensorData {

    public int type;
    public int value;
    public long timestamp;

    public SensorData(int type, int value, long timestamp) {
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }
}
