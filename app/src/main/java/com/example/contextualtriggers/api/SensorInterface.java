package com.example.contextualtriggers.api;

import java.sql.Timestamp;

public interface SensorInterface {

    int getSensorType();

    double getSensorValue();

    Timestamp getTimestamp();

    void setSensorType(int sensorType);

    void setSensorValue(double sensorValue);

    void setTimestamp(Timestamp timestamp);
}
