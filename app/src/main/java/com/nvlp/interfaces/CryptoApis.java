package com.nvlp.interfaces;

import com.nvlp.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoApis {

    //Api for Most Popular Articles
    @GET("http://54.179.251.89:8080/login")
    Call<LoginResponse> login();

}
