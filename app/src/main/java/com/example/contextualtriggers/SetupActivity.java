package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.sensors.StepCounter;
import com.example.contextualtriggers.sensors.Location;
import com.example.contextualtriggers.triggers.StepTrigger;
import com.example.contextualtriggers.api.TriggerManager;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Add sensors to API
        ContextAPI api = ContextAPI.instance;
        api.addSensor(StepCounter.instance);
        api.addSensor(Location.instance);

        // Add triggers to TriggerManager
        TriggerManager triggerManager = TriggerManager.instance;
        triggerManager.addTrigger(new StepTrigger());

        //Request Permission from user to access location data

    }
}