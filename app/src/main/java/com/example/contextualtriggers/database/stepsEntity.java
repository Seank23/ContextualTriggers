package com.example.contextualtriggers.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "steps_table")
public class stepsEntity {

    @PrimaryKey(autoGenerate = true)
    int ID;

    int stepCount;

    String timestamp;

    public stepsEntity(int stepCount, String timestamp) {
        this.stepCount = stepCount;
        this.timestamp = timestamp;
    }

    public int getStepCount() {
        return stepCount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
