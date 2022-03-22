package com.example.contextualtriggers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.contextualtriggers.api.ContextAPI;
import com.example.contextualtriggers.api.StepCounter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent context = new Intent(this, ContextAPI.class);
        startService(context);

    }
}