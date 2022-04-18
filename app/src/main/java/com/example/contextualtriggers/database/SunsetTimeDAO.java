package com.example.contextualtriggers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SunsetTimeDAO {

    @Insert
    void insert(SunsetTimeEntity sunsetTimeEntity);

    @Query("SELECT * FROM sunset_table ORDER BY ID DESC LIMIT 1")
    SunsetTimeEntity getLatestSunsetTime();
}
