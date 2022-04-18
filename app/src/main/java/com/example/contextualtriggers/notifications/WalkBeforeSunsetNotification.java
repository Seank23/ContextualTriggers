package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.R;
import com.example.contextualtriggers.api.NotificationInterface;

import java.util.ArrayList;

public class WalkBeforeSunsetNotification implements NotificationInterface {

    private ArrayList<String> args;

    public WalkBeforeSunsetNotification(ArrayList<String> args) {
        this.args = args;
    }

    @Override
    public String getTitle() {
        return "There's still time to go for a walk!";
    }

    @Override
    public String getText() {
        return "The sun sets in %s so go out for a walk while you can.".format(args.get(0));
    }

    @Override
    public int getIcon() {
        return R.drawable.notificationlogo;
    }
}
