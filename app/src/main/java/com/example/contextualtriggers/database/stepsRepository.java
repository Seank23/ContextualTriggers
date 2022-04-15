package com.example.contextualtriggers.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class stepsRepository {
    private stepsDAO sDao;
    private notificationDAO nDao;

    private stepsEntity latestStepCount;
    private List<stepsEntity> stepsTable;
    private notificationEntity latestNotification;

    public stepsRepository(Application app) {
        Database db = Database.getInstance(app);
        sDao = db.sDAO();
        nDao = db.nDAO();

        
        latestStepCount = sDao.getLastStepCount();
        stepsTable = sDao.getStepsTable();
        latestNotification = nDao.getLatestNotification();



    }

    public void insert(stepsEntity steps) {
        new InsertStepsASyncTask(sDao).execute(steps);
    }

    public void insert(notificationEntity notification) {new InsertNotificationASyncTask(nDao).execute(notification);}

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
}
