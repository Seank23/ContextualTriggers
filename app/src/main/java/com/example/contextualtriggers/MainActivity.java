package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.ServiceManager;
import com.example.contextualtriggers.api.StepCounter;
import com.example.contextualtriggers.notification.NotificationManager;
import com.example.contextualtriggers.triggers.TriggerInterface;
import com.example.contextualtriggers.triggers.TriggerManager;

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
    }
}