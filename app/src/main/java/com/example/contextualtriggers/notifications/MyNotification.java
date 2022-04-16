package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.R;
import com.example.contextualtriggers.api.NotificationInterface;

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
