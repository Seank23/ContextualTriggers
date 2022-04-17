package com.example.contextualtriggers.api;

import android.app.NotificationChannel;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class NotificationManager extends Service {
    Context c;

    public static NotificationManager instance;

    private static final int NOTIFICATION_TIMEOUT = 3600000; // 1 hour

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void sendNotification(HashMap<Integer, NotificationInterface> triggerNotifications) {

        Object[] activeTriggers = triggerNotifications.keySet().toArray();
        Map<Integer, Integer> notificationsSent = ContextAPI.instance.getNotificationsSent();

        // Add new triggers to array with frequency of 0
        for(int i = 0; i < activeTriggers.length; i++) {
            if(!notificationsSent.containsKey((int)activeTriggers[i]))
                notificationsSent.put((int)activeTriggers[i], 0);
        }

        LinkedHashMap<Integer, Integer> orderedFrequencies = new LinkedHashMap<>();

        // Sort notification frequencies ascending
        notificationsSent.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> orderedFrequencies.put(x.getKey(), x.getValue()));

        // Trigger is only valid if its latest notification was sent a while ago
        ArrayList<Integer> validTriggers = new ArrayList<>();
        for(int id : orderedFrequencies.keySet()) {
            String latestTimestamp = ContextAPI.instance.getLatestNotificationTimeByID(id);
            if(latestTimestamp == null) { // No previous notification; so valid
                validTriggers.add(id);
                continue;
            }
            if(System.currentTimeMillis() - Timestamp.valueOf(latestTimestamp).getTime() > NOTIFICATION_TIMEOUT)
                validTriggers.add(id);
        }

        if(validTriggers.isEmpty()) {
            System.out.println("No notification was sent");
            return;
        }

        int sendID = validTriggers.get(0);
        if(triggerNotifications.containsKey(sendID)) {
            System.out.println("Notification (ID: " + sendID + ") was sent");
            doNotification(triggerNotifications.get(sendID));
            ContextAPI.instance.recordNotification(sendID);
        }
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
