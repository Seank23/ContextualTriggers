package com.example.contextualtriggers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WeatherDAO {

    @Insert
    void insert(WeatherEntity weatherEntity);

    @Query("SELECT * FROM weather_table ORDER BY ID DESC LIMIT 1")
    WeatherEntity getLatestWeather();
}
