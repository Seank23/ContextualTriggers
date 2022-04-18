package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.R;
import com.example.contextualtriggers.framework.NotificationInterface;

public class WalkBeforeSunsetNotification implements NotificationInterface {

    private String[] args;

    public WalkBeforeSunsetNotification(String[] args) {
        this.args = args;
    }

    @Override
    public String getTitle() {
        return "There's still time to go for a walk!";
    }

    @Override
    public String getText() {
        return String.format("The sun sets in %s, so why not go out for a walk while you can?", args[0]);
    }

    @Override
    public int getIcon() { return R.drawable.notificationlogo; }
}
