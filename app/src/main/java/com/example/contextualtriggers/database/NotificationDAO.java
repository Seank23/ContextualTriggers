package com.example.contextualtriggers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotificationDAO {

    @Insert
    void insert(NotificationEntity notificationEntity);

    @Query("SELECT * FROM notification_table ORDER BY ID DESC LIMIT 1")
    NotificationEntity getLatestNotification();

    @Query("SELECT notificationID FROM notification_table ORDER BY ID DESC")
    int[] getAllNotification();

    @Query("SELECT notificationTimestamp FROM notification_table WHERE notificationID = :id ORDER BY ID DESC")
    String getLatestNotificationTimeByID(int id);
}
