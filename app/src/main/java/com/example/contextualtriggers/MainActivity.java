package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;

import com.example.contextualtriggers.Workers.TriggerWorker;
import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.ServiceManager;
import com.example.contextualtriggers.api.StepCounter;
import com.example.contextualtriggers.api.Weather;
import com.example.contextualtriggers.notification.NotificationManager;
import com.example.contextualtriggers.triggers.TriggerInterface;
import com.example.contextualtriggers.triggers.TriggerManager;

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

        startService(new Intent(this, Weather.class));

        // Start setup
        startActivity(new Intent(this, SetupActivity.class));

        //Start WorkManager
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(TriggerWorker.class, 15, TimeUnit.MINUTES).setInitialDelay(1, TimeUnit.MINUTES).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
    }
}