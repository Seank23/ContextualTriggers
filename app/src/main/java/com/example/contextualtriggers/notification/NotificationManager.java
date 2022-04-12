package com.example.contextualtriggers.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.contextualtriggers.MainActivity;
import com.example.contextualtriggers.database.notificationEntity;
import com.example.contextualtriggers.database.stepsRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NotificationManager extends Service {
    Context c;

    public static NotificationManager instance;

    private static final int STEPS_TRIGGER_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void sendNotification(HashMap<Integer, Boolean> triggers) {
        Set<Integer> IDs = triggers.keySet();
        //System.out.println("RECEIVED CALL FOR NOTIFICATION");
        stepsRepository sr = new stepsRepository(getApplication());
        notificationEntity ne = new notificationEntity(String.valueOf(new Timestamp(System.currentTimeMillis())),1);

        int lastNotificationID = sr.getLatestNotification().getNotificationID();
        String lastNotificationTimestamp = sr.getLatestNotification().getNotificationTimestamp();
        long tempLastValue = Timestamp.valueOf(lastNotificationTimestamp).getTime();
        long tempNewValue = new Timestamp(System.currentTimeMillis()).getTime();

        long diff = tempNewValue-tempLastValue;
        if (diff>3600000) {
            System.out.println("HOUR PASSED");

            doNotification();

            notificationEntity notification = new notificationEntity(String.valueOf(new Timestamp(System.currentTimeMillis())),1);
            sr.insert(notification);
            //At this point a notifiation should be sent as an hour has passed, so the user will not be bombarded with notifications
            //Will depend on the triggers that have been triggered, and will favour ones that have not had notifications, other than ones that have sent a notification the last time
        }
        //System.out.println("NOTIFICATION ID: "+lastNotificationID);
        //System.out.println("NOTIFICATION TIMESTAMP: "+lastNotificationTimestamp);
    }


    public void doNotification() {
        //Need to do notification here
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
