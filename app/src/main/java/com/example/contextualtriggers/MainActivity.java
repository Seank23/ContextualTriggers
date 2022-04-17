package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.contextualtriggers.Workers.TriggerWorker;
import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.ServiceManager;
import com.example.contextualtriggers.sensors.StepCounter;
import com.example.contextualtriggers.sensors.Location;
import com.example.contextualtriggers.api.NotificationManager;
import com.example.contextualtriggers.api.TriggerManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        // Start Framework Services
        startService(new Intent(this, ServiceManager.class));
        startService(new Intent(this, ContextAPI.class));
        startService(new Intent(this, TriggerManager.class));
        startService(new Intent(this, NotificationManager.class));

        // Start sensors
        startService(new Intent(this, StepCounter.class));
        startService(new Intent(this, Location.class));

        // Start setup
        startActivity(new Intent(this, SetupActivity.class));

        //Start WorkManager
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(TriggerWorker.class, 15, TimeUnit.MINUTES).setInitialDelay(10, TimeUnit.SECONDS).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
    }
}