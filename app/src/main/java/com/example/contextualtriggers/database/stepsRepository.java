package com.example.contextualtriggers.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class stepsRepository {
    private stepsDAO sDao;
    private notificationDAO nDao;
    private WeatherDAO wDao;

    private stepsEntity latestStepCount;
    private List<stepsEntity> stepsTable;
    private notificationEntity latestNotification;
    private int[] notificationIds;
    private WeatherEntity latestWeather;

    public stepsRepository(Application app) {
        Database db = Database.getInstance(app);
        sDao = db.sDAO();
        nDao = db.nDAO();
        wDao = db.wDAO();

        latestStepCount = sDao.getLastStepCount();
        stepsTable = sDao.getStepsTable();
        latestNotification = nDao.getLatestNotification();
        notificationIds = nDao.getAllNotification();
        latestWeather = wDao.getLatestWeather();
    }

    public void insert(stepsEntity steps) {
        new InsertStepsASyncTask(sDao).execute(steps);
    }

    public void insert(notificationEntity notification) {new InsertNotificationASyncTask(nDao).execute(notification);}

    public void insert(LocationEntity location) { new InsertWeatherASyncTask(wDao).execute(location);}

    public stepsEntity getLatestStepCount() {
        //System.out.println("LATEST STEPS COUNT: "+latestStepCount.getStepCount());
        //System.out.println("LATEST STEPS COUNT: "+latestStepCount.getTimestamp());
        //System.out.println("LIST SIZE: "+latestStepCount.size());
        return latestStepCount;
    }

    public List<stepsEntity> getStepsTable() {
        return stepsTable;
    }

    public notificationEntity getLatestNotification() {
        return latestNotification;
    }

    public int[] getNotificationIds() { return notificationIds; }

    public WeatherEntity getLatestWeather() { return latestWeather; }


    private static class InsertStepsASyncTask extends AsyncTask<stepsEntity, Void, Void> {

        private stepsDAO sDao;

        private InsertStepsASyncTask(stepsDAO sDao) {
            this.sDao = sDao;
        }

        @Override
        protected Void doInBackground(stepsEntity... stepsEntities) {
            sDao.insert(stepsEntities[0]);

            System.out.println("Value added: "+stepsEntities[0].getStepCount());
            System.out.println("TIMESTAMP: "+stepsEntities[0].getTimestamp());
            return null;
        }
    }

    private static class InsertNotificationASyncTask extends AsyncTask<notificationEntity, Void, Void> {

        private notificationDAO nDao;
        private InsertNotificationASyncTask(notificationDAO nDao) {this.nDao = nDao;}

        @Override
        protected Void doInBackground(notificationEntity... notificationEntities) {
            nDao.insert(notificationEntities[0]);
            System.out.println("Notification added to DB");
            return null;
        }
    }

//    private static class InsertLocationASyncTask extends AsyncTask<LocationEntity,Void,Void> {
//        private WeatherDAO wDao;
//        private InsertLocationASyncTask(WeatherDAO lDao) {this.wDao = lDao;}
//
//        @Override
//        protected Void doInBackground(LocationEntity... locationEntities) {
//            wDao.insert(locationEntities[0]);
//            System.out.println("Location updated and inserted to DB");
//            return null;
//        }
//    }

    private static class InsertWeatherASyncTask extends AsyncTask<LocationEntity,Void,Void> {
        private WeatherDAO wDao;
        private InsertWeatherASyncTask(WeatherDAO wDao) {this.wDao = wDao;}

        @Override
        protected Void doInBackground(LocationEntity... locationEntities) {
            String key = "f7e3d5a17a72e8f77d6e476a702329bf";
            String exclude = "current,minutely,daily,alerts";
            String str = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=%s&appid=%s",
                    locationEntities[0].getLatitude(), locationEntities[0].getLongitude(), exclude, key);
            String response, weather = null;
            try {
                URI myUri = new URI(str);
                response = getResponseFromHttpUrl(myUri.toURL());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    weather = (String)jsonObject.getJSONArray("hourly").getJSONObject(0)
                            .getJSONArray("weather").getJSONObject(0).get("main");
                }catch (JSONException err){
                    Log.d("Error", err.toString());
                }
                WeatherEntity weatherEntity = new WeatherEntity(System.currentTimeMillis(), weather);
                wDao.insert(weatherEntity);
                System.out.println("Weather updated and inserted to DB");
                System.out.println(weather);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
