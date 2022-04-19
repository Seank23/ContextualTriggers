package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.R;
import com.example.contextualtriggers.framework.NotificationInterface;

public class GoodWeatherMetTargetNotification implements NotificationInterface {
    @Override
    public String getTitle() {
        return "The Weather is Nice!";
    }

    @Override
    public String getText() {
        return "You have reached your step target but the weather is still looking nice, why not go for a walk?";
    }

    @Override
    public int getIcon() {
        return R.drawable.notificationlogo;
    }
}
