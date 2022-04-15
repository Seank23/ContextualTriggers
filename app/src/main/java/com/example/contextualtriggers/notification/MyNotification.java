package com.example.contextualtriggers.notification;

import com.example.contextualtriggers.R;

public class MyNotification implements NotificationInterface {

    @Override
    public String getTitle() {
        return "Do more steps you fat bitch!";
    }

    @Override
    public String getText() {
        return "You haven't walked in over an hour! You fat.";
    }

    @Override
    public int getIcon() {
        return R.drawable.notificationlogo;
    }
}
