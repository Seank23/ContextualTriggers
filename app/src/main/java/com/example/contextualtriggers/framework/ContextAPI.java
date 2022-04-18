package com.example.contextualtriggers.framework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.database.LocationEntity;
import com.example.contextualtriggers.database.NotificationEntity;
import com.example.contextualtriggers.database.SunsetTimeEntity;
import com.example.contextualtriggers.database.WeatherEntity;
import com.example.contextualtriggers.database.StepsEntity;
import com.example.contextualtriggers.database.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ContextAPI extends Service implements ChangeListener {

    public static ContextAPI instance;
    private Repository sr;
    private ArrayList<SensorInterface> sensors;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sensors = new ArrayList<>();
        sr = new Repository(getApplication());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void addSensor(SensorInterface sensor) {
        sensor.setChangeListener(this);
        sensors.add(sensor);
    }

    public void removeSensor(SensorInterface sensor) {
        sensors.remove(sensor);
    }

    public ArrayList<Integer> getSensorTypes() {
        ArrayList<Integer> types = new ArrayList<>();
        for(SensorInterface sensor : sensors)
            types.add(sensor.getSensorType());
        return types;
    }

    public HashSet<Integer> getDataBindings() {
        return sr.getDataBindings();
    }

    public void recordNotification(int id) {
        NotificationEntity notification = new NotificationEntity(String.valueOf(new Timestamp(System.currentTimeMillis())), id);
        sr.insert(notification);
    }

    public HashMap<Integer, Integer> getNotificationsSent() {

        HashMap<Integer, Integer> notificationsSent = new HashMap<>();
        int[] notificationIds = sr.getNotificationIds();
        for(int id : notificationIds) {
            if(notificationsSent.containsKey(id)) {
                int val = notificationsSent.get(id);
                val++;
                notificationsSent.put(id, val);
            } else
                notificationsSent.put(id, 1);
        }
        return notificationsSent;
    }

    public NotificationEntity getLatestNotification() {
        return sr.getLatestNotification();
    }

    public String getLatestNotificationTimeByID(int id) {
        return  sr.getLatestNotificationTimeByID(id);
    }

    public HashMap<Integer, SensorData> getData() {

        List<StepsEntity> steps = sr.getStepsTable();
        WeatherEntity weather = sr.getLatestWeather();
        SunsetTimeEntity sunset = sr.getLatestSunsetTime();
        HashMap<Integer, SensorData> allData = new HashMap<>();

        // Steps data
        SensorData stepSensorData = new SensorData(0, new ArrayList<>(), new ArrayList<>());
        if(steps != null) {
            sr.setDataBinded(0);
            for (StepsEntity step : steps) {
                stepSensorData.values.add(step.getStepCount());
                stepSensorData.timestamps.add(step.getTimestamp());
            }
        }

        // Weather data
        SensorData weatherSensorData = new SensorData(1, new ArrayList<>(), new ArrayList<>());
        if(weather != null) {
            sr.setDataBinded(1);
            weatherSensorData.values.add(weather.getWeather());
            weatherSensorData.timestamps.add(weather.getTimestamp());
        }

        // Sunset data
        SensorData sunsetSensorData = new SensorData(1, new ArrayList<>(), new ArrayList<>());
        if(sunset != null) {
            sr.setDataBinded(2);
            sunsetSensorData.values.add(sunset.getTime());
            sunsetSensorData.timestamps.add(sunset.getTimestamp());
        }

        System.out.println("------");
        System.out.println("Steps:");
        for(Object val : stepSensorData.values)
            System.out.print(val + " ");
        System.out.println("\nWeather:");
        for(Object val : weatherSensorData.values)
            System.out.print(val + " ");
        System.out.println("\nSunset:");
        for(Object val : sunsetSensorData.values)
            System.out.print(val + " ");
        System.out.println("\n------");

        allData.put(0, stepSensorData);
        allData.put(1, weatherSensorData);
        allData.put(2, sunsetSensorData);
        return allData;
    }

    @Override
    public void onChangeHappened(int type) {

        Object value = sensors.get(type).getSensorValue();
        long timestamp = sensors.get(type).getTimestamp();

        switch (type) {
            case 0:
                sr.insert(new StepsEntity((int)value, timestamp));
                break;
            case 1:
                double[] location = (double[])value;
                sr.insert(new LocationEntity(timestamp, location[0], location[1]));
                System.out.println("LOCATION: " + location[0] + ", " + location[1]);
                break;
        }
    }
}
