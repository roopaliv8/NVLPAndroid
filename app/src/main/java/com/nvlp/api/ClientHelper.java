package com.nvlp.api;


import com.nvlp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ClientHelper {

    // timeout limit 2min
    private static final int TIME_OUT = 2;
    private static OkHttpClient.Builder httpClient;

    public ClientHelper() {
    }

    /**
     * Get OKHttpClient Instance (Singleton)
     * Base implementation
     *
     * @return
     */
    private static synchronized OkHttpClient.Builder getInstance() {
        if (httpClient != null) return httpClient;

        httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(TIME_OUT, TimeUnit.MINUTES);
        httpClient.connectTimeout(TIME_OUT, TimeUnit.MINUTES);
        httpClient.writeTimeout(TIME_OUT, TimeUnit.MINUTES);
        if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(InterceptorHelper.loggingInterceptor());
        }
        return httpClient;
    }

    /**
     * OkHttpClient.Builder with auth interceptor
     *
     * @return
     */
    public static synchronized OkHttpClient.Builder withAuthInterceptor() {
        getInstance().addInterceptor(InterceptorHelper.getAuthInterceptor());
        return httpClient;
    }

    static synchronized OkHttpClient.Builder withAuthTokenInterceptor() {
        getInstance().addInterceptor(InterceptorHelper.getInterceptor());
        return httpClient;
    }
}
