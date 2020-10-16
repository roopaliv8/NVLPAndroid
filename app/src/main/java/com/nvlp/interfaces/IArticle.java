package com.nvlp.interfaces;

import com.nvlp.model.response.ArticleResponse;

import retrofit2.Response;

public interface IArticle extends IView {

    void onsuccess(Response<ArticleResponse> response);


}
