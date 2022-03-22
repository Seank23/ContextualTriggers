package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ServiceManager extends Service {

    private ContextAPI api;
    private Map<String, Object> currentData;

    public ServiceManager() {
        currentData = new HashMap<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
