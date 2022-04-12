package com.example.contextualtriggers.Workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.contextualtriggers.api.ServiceManager;

public class TriggerWorker extends Worker {
    public TriggerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("THIS IS A TEST WORKER");
        ServiceManager.instance.handleCheckTriggers();
        return Result.success();
    }
}
