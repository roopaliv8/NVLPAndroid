package com.nvlp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class BaseApp extends Application {
    @SuppressLint("StaticFieldLeak")
    public static OkHttpClient httpClient;
    public static boolean isAppUpdateDailogLoaded;
    @SuppressLint("StaticFieldLeak")
    private static BaseApp appInstance;

    public static BaseApp getInstance() {
        return appInstance;
    }


    public static Context getAppContext() {
        return appInstance.getApplicationContext();
    }

    public static synchronized BaseApp getInstanceSyn() {
        return appInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        isAppUpdateDailogLoaded = false;
        initializehttp();


    }

    private void initializehttp() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        long SIZE_OF_CACHE = 10 * 1024 * 1024;
        Cache cache = new Cache(new File(getBaseContext().getCacheDir(), "http"), SIZE_OF_CACHE);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.MINUTES)
                .cache(cache)
                .retryOnConnectionFailure(true);
        httpClient = builder.build();
    }


}
