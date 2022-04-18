package com.example.contextualtriggers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StepsDAO {

    @Insert
    void insert(StepsEntity steps);

    @Query("SELECT * FROM steps_table ORDER BY ID DESC LIMIT 1")
    StepsEntity getLastStepCount();

    @Query("SELECT * FROM steps_table ORDER BY ID DESC")
    List<StepsEntity> getStepsTable();
}
