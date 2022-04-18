package com.example.contextualtriggers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.example.contextualtriggers.Workers.TriggerWorker;
import com.example.contextualtriggers.framework.ContextAPI;
import com.example.contextualtriggers.framework.ServiceManager;
import com.example.contextualtriggers.sensors.StepCounter;
import com.example.contextualtriggers.sensors.Location;
import com.example.contextualtriggers.framework.NotificationManager;
import com.example.contextualtriggers.framework.TriggerManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACTIVITY_RECOGNITION},2);
        }
        else {
            startServices();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==2) {
            startServices();
        }
    }

    public void startServices() {
        // Start Framewrk Services
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
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(TriggerWorker.class, 15, TimeUnit.MINUTES).setInitialDelay(20, TimeUnit.SECONDS).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
    }
}