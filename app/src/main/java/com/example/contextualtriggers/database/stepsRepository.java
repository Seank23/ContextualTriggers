package com.example.contextualtriggers.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class stepsRepository {
    private stepsDAO sDao;
    private stepsEntity latestStepCount;

    public stepsRepository(Application app) {
        Database db = Database.getInstance(app);
        sDao = db.sDAO();

        //Need to get LiveData working for this, should be in background but will only work in main thread
        latestStepCount = sDao.getLastStepCount();



    }

    public void insert(stepsEntity steps) {
        new InsertStepsASyncTask(sDao).execute(steps);
    }

    public stepsEntity getLatestStepCount() {
        //System.out.println("LATEST STEPS COUNT: "+latestStepCount.getStepCount());
        //System.out.println("LATEST STEPS COUNT: "+latestStepCount.getTimestamp());
        //System.out.println("LIST SIZE: "+latestStepCount.size());
        return latestStepCount;
    }


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
}
