package com.example.contextualtriggers.api;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Weather extends Service {

    final static String baseURL = "";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri buildUri = Uri.parse(baseURL).buildUpon()
                .build();
        System.out.println(buildUri.toString());

        return super.onStartCommand(intent, flags, startId);
    }
}
