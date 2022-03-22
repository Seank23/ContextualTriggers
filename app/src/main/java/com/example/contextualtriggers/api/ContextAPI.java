package com.example.contextualtriggers.api;

import java.util.ArrayList;

public class ContextAPI {

    private ArrayList<SensorInterface> sensors;

    public ContextAPI() {
        sensors = new ArrayList<SensorInterface>();
    }

    public void addSensor(SensorInterface sensor) {
        sensors.add(sensor);
    }

    public void removeSensor(SensorInterface sensor) {
        sensors.remove(sensor);
    }


}
