package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.StepCounter;
import com.example.contextualtriggers.triggers.StepTrigger;
import com.example.contextualtriggers.triggers.TriggerManager;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Add sensors to API
        ContextAPI api = ContextAPI.instance;
        api.addSensor(StepCounter.instance);

        // Add triggers to TriggerManager
        TriggerManager triggerManager = TriggerManager.instance;
        triggerManager.addTrigger(new StepTrigger());

        //Request Permission from user to access location data
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SetupActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }
}