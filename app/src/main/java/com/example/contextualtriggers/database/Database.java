package com.example.contextualtriggers.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Timestamp;

@androidx.room.Database(entities = {StepsEntity.class, NotificationEntity.class, WeatherEntity.class, SunsetTimeEntity.class}, version=1)
public abstract class Database extends RoomDatabase {

    private static Database instance;
    public abstract StepsDAO sDAO();
    public abstract NotificationDAO nDAO();
    public abstract WeatherDAO wDAO();
    public abstract SunsetTimeDAO stDAO();

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class,"contextDatabase")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDBAsyncTask(instance).execute();
        }
    };

    private static class populateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        private StepsDAO sDao;
        private NotificationDAO nDao;
        private WeatherDAO wDao;
        private SunsetTimeDAO stDao;

        private populateDBAsyncTask(Database db) {
            sDao = db.sDAO();
            nDao = db.nDAO();
            wDao = db.wDAO();
            stDao = db.stDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            sDao.insert(new StepsEntity(0, new Timestamp(1649500790).getTime()));
            nDao.insert(new NotificationEntity("2022-04-09 10:55:02.793",0));
            wDao.insert(new WeatherEntity(System.currentTimeMillis(),"Clouds"));
            wDao.insert(new WeatherEntity(System.currentTimeMillis(), "Clear"));
            stDao.insert(new SunsetTimeEntity(System.currentTimeMillis(),"19:46:00"));
            return null;
        }
    }
}
