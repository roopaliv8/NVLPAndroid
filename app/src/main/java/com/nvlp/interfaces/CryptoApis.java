package com.nvlp.interfaces;

import com.nvlp.BuildConfig;
import com.nvlp.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.nvlp.BuildConfig.COMMONPORT;


public interface CryptoApis {

    //Login api to get token
    @GET("http://" + BuildConfig.BASEURL + ":" + COMMONPORT + "/login")
    Call<LoginResponse> login();

}
