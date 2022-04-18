package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.api.NotificationInterface;
import com.example.contextualtriggers.api.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.WalkBeforeSunsetNotification;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SunsetTimeTrigger implements TriggerInterface {

    private ArrayList<String> args = new ArrayList<>();
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public NotificationInterface getNotification() {
        return new WalkBeforeSunsetNotification(args);
    }

    @Override
    public String getSensorsRequired() {
        return "2";
    }

    @Override
    public String[] getArgs() {
        return (String[]) args.toArray();
    }

    @Override
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {

        SensorData sunsetData = data.get(2);
        if(sunsetData == null)
            return false;

        long timeThreshold = 10800; // 3 hours
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
        int difference = Math.abs(getSeconds((String)sunsetData.values.get(0)) - getSeconds(currentTime));

        if(difference < timeThreshold) {
            if(difference < 3600) {
                int roundedTime = (int)(Math.ceil(difference / 900.0) * 900) / 900;
                args.add(roundedTime + " minutes");
            } else {
                int roundedTime = (int)(Math.ceil(difference / 3600.0) * 3600) / 3600;
                args.add(roundedTime + " hours");
            }

            return true;
        }

        return false;
    }

    private int getSeconds(String timeStr) {
        String[] split = timeStr.split(":");
        return Integer.parseInt(split[2]) + Integer.parseInt(split[1]) * 60 + Integer.parseInt(split[0]) * 3600;
    }
}
