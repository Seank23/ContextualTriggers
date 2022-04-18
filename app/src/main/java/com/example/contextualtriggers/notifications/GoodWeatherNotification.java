package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.R;
import com.example.contextualtriggers.framework.NotificationInterface;

public class GoodWeatherNotification implements NotificationInterface {
    @Override
    public String getTitle() {
        return "The Weather is Nice!";
    }

    @Override
    public String getText() {
        return "The weather over the next hour is meant to be nice at your current location! Why not go for a walk and boost your step count?";
    }

    @Override
    public int getIcon() {
        return R.drawable.notificationlogo;
    }
}
