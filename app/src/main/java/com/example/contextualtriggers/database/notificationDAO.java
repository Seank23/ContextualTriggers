package com.example.contextualtriggers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface notificationDAO {

    @Insert
    void insert(notificationEntity notificationEntity);

    @Query("SELECT * FROM notification_table ORDER BY ID DESC LIMIT 1")
    notificationEntity getLatestNotification();
}