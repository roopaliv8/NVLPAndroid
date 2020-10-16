package com.nvlp.api;

import com.nvlp.BaseApp;
import com.nvlp.utils.BasicUtils;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;


public class InterceptorHelper {

    private static HttpLoggingInterceptor logging;
    private static Interceptor authInterceptor;
    private static Interceptor cacheInterceptor;

    /**
     * Log Retrofit request body
     *
     * @return
     */
    static synchronized HttpLoggingInterceptor loggingInterceptor() {
        if (logging != null) return logging;
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    /**
     * Common method for unauthorized chatDBUser
     *
     * @return
     */
    static synchronized Interceptor getAuthInterceptor() {

        if (authInterceptor != null) return authInterceptor;

        authInterceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };
        return authInterceptor;
    }

    /**
     * Common method for unauthorized chatDBUser
     *
     * @return
     */
    static synchronized Interceptor getInterceptor() {

        if (authInterceptor != null) return authInterceptor;

        authInterceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);

        };
        return authInterceptor;
    }


    public static synchronized Interceptor getCacheInterceptor() {

        if (cacheInterceptor != null) return cacheInterceptor;

        cacheInterceptor = chain -> {
            Request request = chain.request();
            if (BasicUtils.isOnline(BaseApp.getAppContext())) {
                request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60 * 20).build();
            } else {
                request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
            }
            return chain.proceed(request);
        };

        return cacheInterceptor;

    }
}
