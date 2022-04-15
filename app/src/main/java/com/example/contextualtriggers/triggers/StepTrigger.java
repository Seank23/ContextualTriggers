package com.example.contextualtriggers.triggers;

import com.example.contextualtriggers.data.SensorData;
import com.example.contextualtriggers.notification.MyNotification;
import com.example.contextualtriggers.notification.NotificationInterface;

import java.util.HashMap;

public class StepTrigger implements TriggerInterface {


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public NotificationInterface getNotification() {
        return new MyNotification();
    }

    @Override
    public String getSensorsRequired() { return "0"; }

    @Override
    public boolean checkTrigger(HashMap<Integer, SensorData> data) {
//        SensorData stepsData = data.get(0);
//        if(stepsData.value < 100) {
//            System.out.println("Trigger activated: " + stepsData.value + " steps");
//            return true;
//        }
//        else {
//            System.out.println("Trigger inactive: " + stepsData.value + " steps");
//            return false;
//        }
        return true;
    }
}
