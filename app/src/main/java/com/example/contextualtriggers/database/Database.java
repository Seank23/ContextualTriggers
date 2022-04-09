package com.example.contextualtriggers.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.sql.Time;
import java.sql.Timestamp;

@androidx.room.Database(entities = {stepsEntity.class}, version=1)
public abstract class Database extends RoomDatabase {

    private static Database instance;
    public abstract stepsDAO sDAO();

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class,"contextDatabase")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

    private static class populateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        private stepsDAO sDao;

        private populateDBAsyncTask(Database db) {
            sDao = db.sDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            sDao.insert(new stepsEntity(0,String.valueOf(new Timestamp(System.currentTimeMillis()))));
            return null;
        }
    }
}
