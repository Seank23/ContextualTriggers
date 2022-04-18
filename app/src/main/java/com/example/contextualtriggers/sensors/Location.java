package com.example.contextualtriggers.sensors;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.contextualtriggers.framework.ChangeListener;
import com.example.contextualtriggers.framework.SensorInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Location extends Service implements SensorInterface {

    public static Location instance;

    private FusedLocationProviderClient client;
    private LocationManager locationManager;
    private LocationListener listener;
    private ChangeListener changeListener;

    private double[] myLocation;
    long timestamp = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        client = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationListener();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,300000,0,listener);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public int getSensorType() {
        return 1;
    }

    @Override
    public Object getSensorValue() {
        return myLocation;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setChangeListener(ChangeListener listener) {
        this.changeListener = listener;
    }

    public class LocationListener implements android.location.LocationListener {

        @Override
        public void onLocationChanged(@NonNull android.location.Location location) {

            myLocation = new double[] { location.getLatitude(), location.getLongitude() };
            timestamp = System.currentTimeMillis();
            if (changeListener != null) {
                changeListener.onChangeHappened(getSensorType());
            }
        }
    }
}
