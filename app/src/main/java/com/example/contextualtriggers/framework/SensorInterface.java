package com.example.contextualtriggers.framework;

public interface SensorInterface {

    int getSensorType();

    Object getSensorValue();

    long getTimestamp();

    void setChangeListener(ChangeListener listener);
}
