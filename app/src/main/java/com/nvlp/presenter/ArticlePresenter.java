package com.nvlp.presenter;



import com.nvlp.api.APIHelper;
import com.nvlp.api.ServiceGenerator;
import com.nvlp.interfaces.ArticleApis;
import com.nvlp.interfaces.IArticle;
import com.nvlp.model.response.ArticleResponse;
import com.nvlp.utils.BasicUtils;

import java.net.HttpURLConnection;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlePresenter extends BasePresenter<IArticle> {

    private ArticleApis service = ServiceGenerator.createServiceWithCache(ArticleApis.class);

    public void getArticles() {

        if (BasicUtils.isOnline(getView().getContext())) {

            getView().enableLoadingBar(true);

            Call<ArticleResponse> getArticles = service.getArticles();

            APIHelper.enqueueWith(getArticles, new Callback<ArticleResponse>() {
                @Override
                public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull Response<ArticleResponse> response) {
                    getView().enableLoadingBar(false);

                    if (response.body() != null) {

                        if (response.code() == HttpURLConnection.HTTP_OK && response.body().getStatus().equalsIgnoreCase("OK")) {
                            getView().onsuccess(response);
                        } else {
                            handleError(response.message());
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {
                    getView().enableLoadingBar(false);
                    handleError(t.getMessage());
                }


            });
        }
    }


}
