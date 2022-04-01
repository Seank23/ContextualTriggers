package com.example.contextualtriggers.api;

import java.sql.Timestamp;

public interface SensorInterface {

    int getSensorType();

    int getSensorValue();

    long getTimestamp();

    void setSensorType(int sensorType);

    void setSensorValue(int sensorValue);

    void setTimestamp(long timestamp);

    void setChangeListener(ChangeListener listener);
}
