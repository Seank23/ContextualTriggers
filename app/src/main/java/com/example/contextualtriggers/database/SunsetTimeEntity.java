package com.example.contextualtriggers.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sunset_table")
public class SunsetTimeEntity {

    @PrimaryKey(autoGenerate = true)
    int ID;

    long timestamp;

    String sunsetTime;

    public SunsetTimeEntity(long timestamp, String sunsetTime) {
        this.timestamp = timestamp;
        this.sunsetTime = sunsetTime;
    }

    public int getID() {
        return ID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTime() {
        return sunsetTime;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }
}
