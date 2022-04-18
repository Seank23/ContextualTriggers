package com.example.contextualtriggers.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_table")
public class NotificationEntity {

    @PrimaryKey(autoGenerate = true)
    int ID;

    String notificationTimestamp;

    int notificationID;

    public NotificationEntity(String notificationTimestamp, int notificationID) {
        this.notificationID = notificationID;
        this.notificationTimestamp = notificationTimestamp;
    }

    public String getNotificationTimestamp() {
        return notificationTimestamp;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNotificationTimestamp(String timestamp) {
        this.notificationTimestamp = timestamp;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
}
