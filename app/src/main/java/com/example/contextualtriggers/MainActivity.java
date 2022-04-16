package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.contextualtriggers.Workers.TriggerWorker;
import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.ServiceManager;
import com.example.contextualtriggers.sensors.StepCounter;
import com.example.contextualtriggers.sensors.Weather;
import com.example.contextualtriggers.api.NotificationManager;
import com.example.contextualtriggers.api.TriggerManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, ServiceManager.class));

        // Start ContextAPI
        startService(new Intent(this, ContextAPI.class));


        startService(new Intent(this, TriggerManager.class));


        startService(new Intent(this, NotificationManager.class));

        // Start sensors
        startService(new Intent(this, StepCounter.class));

        // Start setup
        startActivity(new Intent(this, SetupActivity.class));

        startService(new Intent(this, Weather.class));

        //Start WorkManager
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(TriggerWorker.class, 15, TimeUnit.MINUTES).setInitialDelay(1, TimeUnit.MINUTES).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
    }
}