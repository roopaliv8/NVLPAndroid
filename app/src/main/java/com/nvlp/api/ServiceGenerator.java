package com.nvlp.api;


import com.nvlp.BaseApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.File;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nvlp.utils.Constants.BASEURL;

/**
 * Retrofit Service generator
 */

public class ServiceGenerator {
    private static final String API_BASE_URL = BASEURL;
    //    private static final String API_BASE_URL = "";
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;

    // log rest calls
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createServiceWithHeaders(Class<S> serviceClass) {

        httpClient = ClientHelper.withAuthTokenInterceptor();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        if (retrofit == null)
            retrofit = builder.client(httpClient.build()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {

        httpClient = ClientHelper.withAuthTokenInterceptor();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        if (retrofit == null)
            retrofit = builder.client(httpClient.build()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceWithCache(Class<S> serviceClass) {
        // set your desired log level
        httpClient = ClientHelper.withAuthTokenInterceptor();
        httpClient.cache(new okhttp3.Cache(new File(BaseApp.getAppContext().getCacheDir(), "http"), 10 * 1024 * 1024));
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = gsonBuilder.create();
        if (retrofit == null)
            retrofit = builder.client(httpClient.build()).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit.create(serviceClass);
    }

    public static Retrofit retrofit() {
        return retrofit;
    }
}
