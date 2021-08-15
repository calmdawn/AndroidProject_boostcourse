package com.calmdawn.boostcoursemobileproject.common;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomAsyncTask {

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public void execute(CustomAsyncTaskCallback callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //기존 AsyncTask doInBackground
                callback.doInBackground();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //기존 AsyncTask postExecute
                        callback.postExecute();
                    }
                });

            }
        });
    }


}
