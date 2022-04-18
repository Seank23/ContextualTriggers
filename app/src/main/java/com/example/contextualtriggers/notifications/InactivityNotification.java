package com.example.contextualtriggers.notifications;

import com.example.contextualtriggers.R;
import com.example.contextualtriggers.framework.NotificationInterface;

public class InactivityNotification implements NotificationInterface {

    @Override
    public String getTitle() {
        return "Time to Walk!";
    }

    @Override
    public String getText() {
        return "You haven't walked in over an hour! Why not go for a quick walk round the block?";
    }

    @Override
    public int getIcon() {
        return R.drawable.notificationlogo;
    }
}
