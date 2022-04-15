package com.example.contextualtriggers.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "steps_table")
public class stepsEntity {

    @PrimaryKey(autoGenerate = true)
    int ID;

    int stepCount;

    long timestamp;

    public stepsEntity(int stepCount, long timestamp) {
        this.stepCount = stepCount;
        this.timestamp = timestamp;
    }

    public int getStepCount() {
        return stepCount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
