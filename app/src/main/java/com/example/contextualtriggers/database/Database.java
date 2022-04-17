package com.example.contextualtriggers.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Timestamp;

@androidx.room.Database(entities = {stepsEntity.class, notificationEntity.class, WeatherEntity.class}, version=1)
public abstract class Database extends RoomDatabase {

    private static Database instance;
    public abstract stepsDAO sDAO();
    public abstract notificationDAO nDAO();
    public abstract WeatherDAO wDAO();

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
        private stepsDAO sDao;
        private notificationDAO nDao;
        private WeatherDAO wDao;

        private populateDBAsyncTask(Database db) {
            sDao = db.sDAO();
            nDao = db.nDAO();
            wDao = db.wDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            sDao.insert(new stepsEntity(2,new Timestamp(System.currentTimeMillis()).getTime()));
            nDao.insert(new notificationEntity("2022-04-09 10:55:02.793",0));
            wDao.insert(new WeatherEntity(System.currentTimeMillis(),"Clear"));
            return null;
        }
    }
}
