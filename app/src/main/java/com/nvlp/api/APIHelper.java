package com.nvlp.api;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.nvlp.BaseApp;
import com.nvlp.R;
import com.nvlp.model.response.ErrorParser;

import java.io.IOException;
import java.net.HttpURLConnection;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIHelper {

    private static Gson gson;

    public static <T> void enqueueWith(Call<T> call, final Callback<T> callback) {

        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

                switch (response.code()) {

                    case HttpURLConnection.HTTP_OK:
                    case HttpURLConnection.HTTP_CREATED:
                    case HttpURLConnection.HTTP_CONFLICT:
                        callback.onResponse(call, response);
                        break;

                    case HttpURLConnection.HTTP_UNAUTHORIZED:

                        try {
                            gson = new Gson();
                            ErrorParser mErrorParser;
                            if (response.errorBody() != null) {
                                mErrorParser = gson.fromJson(response.errorBody().string(), ErrorParser.class);

                                callback.onFailure(call, new ApiException(mErrorParser.getMessage()));
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            callback.onFailure(call, new ApiException(BaseApp.getAppContext().getResources().getString(R.string.something_wrong_alert)));
                        }

                        break;

                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        try {
                            gson = new Gson();
                            ErrorParser mErrorParser;
                            if (response.errorBody() != null) {
                                mErrorParser = gson.fromJson(response.errorBody().string(), ErrorParser.class);
                                callback.onFailure(call, new ApiException(mErrorParser.getMessage()));
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            callback.onFailure(call, new ApiException(BaseApp.getAppContext().getResources().getString(R.string.something_wrong_alert)));
                        }
                        break;

                    case HttpURLConnection.HTTP_BAD_METHOD:
                        break;

                    default:
                        try {
                            gson = new Gson();
                            ErrorParser mErrorParser;
                            if (response.errorBody() != null) {
                                mErrorParser = gson.fromJson(response.errorBody().string(), ErrorParser.class);
                                callback.onFailure(call, new ApiException(mErrorParser.getMessage()));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onFailure(call, new ApiException(BaseApp.getAppContext().getResources().getString(R.string.something_wrong_alert)));
                        }
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                String error = null;
                if (t instanceof IOException) {
                    error = BaseApp.getAppContext().getResources().getString(R.string.error_internet);
                }
                callback.onFailure(call, TextUtils.isEmpty(error) ? t : new ApiException(error));
            }
        });
    }
}