package com.example.contextualtriggers.api;

import android.app.NotificationChannel;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.contextualtriggers.database.notificationEntity;
import com.example.contextualtriggers.database.stepsRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class NotificationManager extends Service {
    Context c;

    public static NotificationManager instance;

    private static final int STEPS_TRIGGER_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void sendNotification(HashMap<Integer, NotificationInterface> triggerNotifications) {

        Object[] activeTriggers = triggerNotifications.keySet().toArray();
        int notificationToSend = 0;

        HashMap<Integer, Integer> notificationsSent = ContextAPI.instance.getNotificationsSent();
        notificationEntity latestNotification = ContextAPI.instance.getLatestNotification();

        if(activeTriggers.length == 1)
            notificationToSend = (int)activeTriggers[0];
        else if(activeTriggers.length > 1) {

            for(int i = 0; i < activeTriggers.length; i++) {
                if(!notificationsSent.containsKey((int)activeTriggers[i]))
                    notificationsSent.put((int)activeTriggers[i], 0);
            }

            notificationsSent.entrySet().stream()
                    .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                    .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));

            if(latestNotification != null)
                notificationsSent.remove(latestNotification.getNotificationID());
            List keys = new ArrayList(notificationsSent.keySet());
            notificationToSend = (int)keys.get(0);
        }

        doNotification(triggerNotifications.get(notificationToSend));
        ContextAPI.instance.recordNotification(notificationToSend);

//        int lastNotificationID = sr.getLatestNotification().getNotificationID();
//        String lastNotificationTimestamp = sr.getLatestNotification().getNotificationTimestamp();
//        long tempLastValue = Timestamp.valueOf(lastNotificationTimestamp).getTime();
//        long tempNewValue = new Timestamp(System.currentTimeMillis()).getTime();
//
//        long diff = tempNewValue-tempLastValue;
//        if (diff>3600000) {
//            System.out.println("HOUR PASSED");
//
//            doNotification(triggerNotifications.get(0));
//
//            ContextAPI.instance.recordNotification();
//            //At this point a notification should be sent as an hour has passed, so the user will not be bombarded with notifications
//            //Will depend on the triggers that have been triggered, and will favour ones that have not had notifications, other than ones that have sent a notification the last time
//        }
//        else {
//            System.out.println("Hour has not passed.");
//        }
        //System.out.println("NOTIFICATION ID: "+lastNotificationID);
        //System.out.println("NOTIFICATION TIMESTAMP: "+lastNotificationTimestamp);
    }


    public void doNotification(NotificationInterface notification) {
        //Need to do notification here
        NotificationChannel channel = new NotificationChannel("notificationChannel", "notificationChannel", android.app.NotificationManager.IMPORTANCE_HIGH);
        android.app.NotificationManager notificationManager = getSystemService(android.app.NotificationManager.class);

        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"notificationChannel")
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getText())
                .setSmallIcon(notification.getIcon());

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1,builder.build());
    }
    



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        c = getApplicationContext();
        System.out.println("");
        return super.onStartCommand(intent, flags, startId);
    }
}
