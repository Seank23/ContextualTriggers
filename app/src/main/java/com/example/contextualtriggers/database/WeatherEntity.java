package com.example.contextualtriggers.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_table")
public class WeatherEntity {

    @PrimaryKey(autoGenerate = true)
    int ID;

    long timestamp;

    String weather;


    public WeatherEntity(long timestamp, String weather) {
        this.timestamp = timestamp;
        this.weather = weather;
    }

    public int getID() {
        return ID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getWeather() {
        return weather;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
