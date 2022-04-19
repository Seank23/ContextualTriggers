package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.contextualtriggers.framework.ContextAPI;
import com.example.contextualtriggers.sensors.StepCounter;
import com.example.contextualtriggers.sensors.Location;
import com.example.contextualtriggers.triggers.GoodWeatherReachTargetTrigger;
import com.example.contextualtriggers.triggers.GoodWeatherMetTargetTrigger;
import com.example.contextualtriggers.triggers.StepTrigger;
import com.example.contextualtriggers.framework.TriggerManager;
import com.example.contextualtriggers.triggers.SunsetTimeTrigger;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


    }

    @Override
    protected void onStart() {
        super.onStart();

        // Add sensors to API
        ContextAPI api = ContextAPI.instance;
        api.addSensor(StepCounter.instance);
        api.addSensor(Location.instance);

        // Add triggers to TriggerManager
        TriggerManager triggerManager = TriggerManager.instance;
        triggerManager.addTrigger(new StepTrigger());
        triggerManager.addTrigger(new GoodWeatherMetTargetTrigger());
        triggerManager.addTrigger(new SunsetTimeTrigger());
        triggerManager.addTrigger(new GoodWeatherReachTargetTrigger());
    }
}