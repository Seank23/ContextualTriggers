package com.example.contextualtriggers.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Repository {
    private StepsDAO sDao;
    private NotificationDAO nDao;
    private WeatherDAO wDao;
    private SunsetTimeDAO stDao;

    private StepsEntity latestStepCount;
    private List<StepsEntity> stepsTable;
    private NotificationEntity latestNotification;
    private int[] notificationIds;
    private WeatherEntity latestWeather;
    private SunsetTimeEntity latestSunsetTime;

    private HashSet<Integer> dataBinded;

    public Repository(Application app) {
        Database db = Database.getInstance(app);
        sDao = db.sDAO();
        nDao = db.nDAO();
        wDao = db.wDAO();
        stDao = db.stDAO();

        latestStepCount = sDao.getLastStepCount();
        stepsTable = sDao.getStepsTable();
        latestNotification = nDao.getLatestNotification();
        notificationIds = nDao.getAllNotification();
        latestWeather = wDao.getLatestWeather();
        latestSunsetTime = stDao.getLatestSunsetTime();
        dataBinded = new HashSet<>();
    }

    public void insert(StepsEntity steps) {
        dataBinded.add(0);
        new InsertStepsASyncTask(sDao).execute(steps);
    }

    public void insert(NotificationEntity notification) {
        new InsertNotificationASyncTask(nDao).execute(notification);
    }

    public void insert(LocationEntity location) {
        dataBinded.add(1);
        dataBinded.add(2);
        new InsertWeatherASyncTask(wDao).execute(location);
        new InsertSunsetTimeASyncTask(stDao).execute(location);
    }

    public StepsEntity getLatestStepCount() {
        return latestStepCount;
    }

    public List<StepsEntity> getStepsTable() {
        return stepsTable;
    }

    public NotificationEntity getLatestNotification() {
        return latestNotification;
    }

    public int[] getNotificationIds() { return notificationIds; }

    public WeatherEntity getLatestWeather() { return latestWeather; }

    public SunsetTimeEntity getLatestSunsetTime() { return latestSunsetTime; }

    public String getLatestNotificationTimeByID(int id) { return nDao.getLatestNotificationTimeByID(id); }

    public HashSet<Integer> getDataBindings() { return dataBinded; }

    public void setDataBinded(int type) { dataBinded.add(type); }

    private static class InsertStepsASyncTask extends AsyncTask<StepsEntity, Void, Void> {

        private StepsDAO sDao;

        private InsertStepsASyncTask(StepsDAO sDao) {
            this.sDao = sDao;
        }

        @Override
        protected Void doInBackground(StepsEntity... stepsEntities) {
            sDao.insert(stepsEntities[0]);

            //System.out.println("Value added: "+stepsEntities[0].getStepCount());
            //System.out.println("TIMESTAMP: "+stepsEntities[0].getTimestamp());
            return null;
        }
    }

    private static class InsertNotificationASyncTask extends AsyncTask<NotificationEntity, Void, Void> {

        private NotificationDAO nDao;
        private InsertNotificationASyncTask(NotificationDAO nDao) {this.nDao = nDao;}

        @Override
        protected Void doInBackground(NotificationEntity... notificationEntities) {
            nDao.insert(notificationEntities[0]);
            System.out.println("Notification added to DB");
            return null;
        }
    }

    private static class InsertWeatherASyncTask extends AsyncTask<LocationEntity,Void,Void> {
        private WeatherDAO wDao;
        private InsertWeatherASyncTask(WeatherDAO wDao) {this.wDao = wDao;}

        @Override
        protected Void doInBackground(LocationEntity... locationEntities) {
            String key = "4fcbf301b36a550245b6bf7516388a58";
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

    private static class InsertSunsetTimeASyncTask extends AsyncTask<LocationEntity,Void,Void> {
        private SunsetTimeDAO stDao;
        private InsertSunsetTimeASyncTask(SunsetTimeDAO stDao) {this.stDao = stDao;}

        @Override
        protected Void doInBackground(LocationEntity... locationEntities) {

            String key = "2d078b62d6cf49debbd62eead6ef7551";
            String str = String.format("https://api.ipgeolocation.io/astronomy?apiKey=%s&lat=%s&long=%s",
                    key, locationEntities[0].getLatitude(), locationEntities[0].getLongitude());
            String response, sunset = null;
            try {
                URI myUri = new URI(str);
                response = getResponseFromHttpUrl(myUri.toURL());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sunset = (String)jsonObject.get("sunset");
                }catch (JSONException err){
                    Log.d("Error", err.toString());
                }
                sunset = sunset + ":00";
                SunsetTimeEntity sunsetTimeEntity = new SunsetTimeEntity(System.currentTimeMillis(), sunset);
                stDao.insert(sunsetTimeEntity);
                System.out.println("Sunset time updated and inserted to DB");
                System.out.println(sunset);

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
