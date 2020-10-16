package com.nvlp.presenter;


import com.nvlp.api.APIHelper;
import com.nvlp.api.ServiceGenerator;
import com.nvlp.interfaces.CryptoApis;
import com.nvlp.interfaces.ILogin;
import com.nvlp.model.response.LoginResponse;
import com.nvlp.utils.BasicUtils;

import java.net.HttpURLConnection;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<ILogin> {

    private CryptoApis service = ServiceGenerator.createServiceWithCache(CryptoApis.class);

    public void login() {

        if (BasicUtils.isOnline(getView().getContext())) {

            getView().enableLoadingBar(true);

            Call<LoginResponse> getArticles = service.login();

            APIHelper.enqueueWith(getArticles, new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    getView().enableLoadingBar(false);

                    if (response.body() != null) {

                        if (response.code() == HttpURLConnection.HTTP_OK) {
                            getView().onsuccess(response.body().getToken());
                        } else {
                            handleError(response.message());
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    getView().enableLoadingBar(false);
                    handleError(t.getMessage());
                }


            });
        }
    }


}
