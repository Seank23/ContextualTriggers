package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.R;
import com.example.contextualtriggers.framework.NotificationInterface;

public class GoodWeatherReachTargetNotification implements NotificationInterface {

    private String[] args;

    public GoodWeatherReachTargetNotification(String[] args) {
        this.args = args;
    }

    @Override
    public String getTitle() {
        return "Reach your step target!";
    }

    @Override
    public String getText() {
        return String.format("The weather is really good right now. Can you reach your target of %s steps for today?", args[0]);
    }

    @Override
    public int getIcon() { return R.drawable.notificationlogo; }
}
