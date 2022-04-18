package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.api.NotificationInterface;

import java.util.ArrayList;

public class GoodWeatherReachTargetNotification implements NotificationInterface {

    private ArrayList<String> args;

    public GoodWeatherReachTargetNotification(ArrayList<String> args) {
        this.args = args;
    }

    @Override
    public String getTitle() {
        return "Reach you step target!";
    }

    @Override
    public String getText() {
        return "The weather is really good right now. Can you reach your target of %s steps for today?".format(args.get(0));
    }

    @Override
    public int getIcon() {
        return 0;
    }
}
