package com.example.contextualtriggers.sensors;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.contextualtriggers.api.ChangeListener;
import com.example.contextualtriggers.api.SensorInterface;
import com.example.contextualtriggers.database.LocationEntity;
import com.example.contextualtriggers.database.stepsRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Weather extends Service implements SensorInterface {

    final static String baseURL = "";

    public static Weather instance;

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
        Uri buildUri = Uri.parse(baseURL).buildUpon()
                .build();
        //System.out.println(buildUri.toString());

        client = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationListener();








        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //double lat = client.getLastLocation().getResult().getLatitude();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,4000,0,listener);

        }




        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public int getSensorType() {
        return 1;
    }

    @Override
    public Object getSensorValue() {
        //Will return 2 values so not sure how to work with this?
        return (Object)myLocation;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setSensorType(int sensorType) {

    }

    @Override
    public void setSensorValue(Object sensorValue) {

    }

    @Override
    public void setTimestamp(long timestamp) {

    }

    @Override
    public void setChangeListener(ChangeListener listener) {
        this.changeListener = listener;
    }

    public class LocationListener implements android.location.LocationListener {


        @Override
        public void onLocationChanged(@NonNull Location location) {

            myLocation = new double[] { location.getLatitude(), location.getLongitude() };
            timestamp = System.currentTimeMillis();
            if (changeListener != null) {
                changeListener.onChangeHappened(getSensorType());
            }
//            System.out.println("LATITUDE: " + myLocation[0]);
//            System.out.println("LONGITUDE: " + myLocation[1]);
//            timestamp = System.currentTimeMillis();
//            LocationEntity entity = new LocationEntity(timestamp, myLocation[0], myLocation[1]);
//            stepsRepository repository = new stepsRepository(getApplication());
//            repository.insert(entity);
//
//            LocationEntity recent = repository.getLatestLocation();
//            System.out.println("RECENT LOCATION: " + recent.getLatitude());
//            System.out.println("TIME: " + recent.getTimestamp());
        }
    }
}
