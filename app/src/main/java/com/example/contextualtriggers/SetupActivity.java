package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.StepCounter;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Get ContextAPI
        ContextAPI api = ContextAPI.instance;
        // Add sensors to API
        api.addSensor(StepCounter.instance);
    }
}