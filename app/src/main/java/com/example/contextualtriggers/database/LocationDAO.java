package com.example.contextualtriggers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LocationDAO {

    @Insert
    void insert(LocationEntity locationEntity);

    @Query("SELECT * FROM location_table ORDER BY ID DESC LIMIT 1")
    LocationEntity getLatestLocation();
}
