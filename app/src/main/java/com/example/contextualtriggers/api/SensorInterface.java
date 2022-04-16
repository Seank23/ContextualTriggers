package com.example.contextualtriggers.api;

public interface SensorInterface {

    int getSensorType();

    Object getSensorValue();

    long getTimestamp();

    void setSensorType(int sensorType);

    void setSensorValue(Object sensorValue);

    void setTimestamp(long timestamp);

    void setChangeListener(ChangeListener listener);
}
