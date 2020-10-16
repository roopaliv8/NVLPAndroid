package com.nvlp.interfaces;

import com.nvlp.model.response.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleApis {

    //Api for Most Popular Articles
    @GET("mostviewed/all-sections/7.json?api-key=b1UjgHUMLOhF8MU13sB5F5WVa4qY74WI")
    Call<ArticleResponse> getArticles();

}
