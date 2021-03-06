package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.Utils;
import com.example.contextualtriggers.framework.NotificationInterface;
import com.example.contextualtriggers.framework.TriggerInterface;
import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notifications.WalkBeforeSunsetNotification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class SunsetTimeTrigger implements TriggerInterface {

    private ArrayList<String> args = new ArrayList<>();
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public NotificationInterface getNotification() {
        return new WalkBeforeSunsetNotification(getArgs());
    }

    @Override
    public String getSensorsRequired() {
        return "2";
    }

    @Override
    public String[] getArgs() {
        return args.toArray(new String[args.size()]);
    }

    @Override
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {

        SensorData sunsetData = data.get(2);
        if(sunsetData == null)
            return false;
        if(sunsetData.values.isEmpty())
            return false;

        long timeThreshold = 10800; // 3 hours
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
        int difference = Utils.getSeconds((String)sunsetData.values.get(0)) - Utils.getSeconds(currentTime);

        if(difference > 0 && difference < timeThreshold &&
                Utils.getSeconds(currentTime) > 61200) { // After 17:00
            if(difference < 3600) {
                int roundedTime = (int)(Math.ceil(difference / 900.0) * 900) / 60;
                args.add(0, roundedTime + " minutes");
            } else {
                int roundedTime = (int)(Math.ceil(difference / 3600.0) * 3600) / 3600;
                args.add(0, roundedTime + " hours");
            }
            return true;
        }
        return false;
    }
}
