package com.example.contextualtriggers.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface stepsDAO {

    @Insert
    void insert(stepsEntity steps);

    @Query("SELECT * FROM steps_table ORDER BY ID DESC LIMIT 1")
    stepsEntity getLastStepCount();

    @Query("SELECT * FROM steps_table ORDER BY ID DESC")
    List<stepsEntity> getStepsTable();
}
