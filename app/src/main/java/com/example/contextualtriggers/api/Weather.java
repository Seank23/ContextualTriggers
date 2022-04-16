package com.example.contextualtriggers.api;

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

import com.example.contextualtriggers.database.LocationEntity;
import com.example.contextualtriggers.database.stepsRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Weather extends Service {

    final static String baseURL = "";

    private FusedLocationProviderClient client;
    private LocationManager locationManager;
    private LocationListener listener;
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
    
    public class LocationListener implements android.location.LocationListener {


        @Override
        public void onLocationChanged(@NonNull Location location) {
            System.out.println("LATITUDE: "+location.getLatitude());
            System.out.println("LONGITUDE: "+location.getLongitude());
            LocationEntity entity = new LocationEntity(System.currentTimeMillis(),location.getLatitude(),location.getLongitude());
            stepsRepository repository = new stepsRepository(getApplication());

            repository.insert(entity);

            LocationEntity recent = repository.getLatestLocation();
            System.out.println("RECENT LOCATION: "+recent.getLatitude());
            System.out.println("TIME: "+recent.getTimestamp());
        }
    }
}
