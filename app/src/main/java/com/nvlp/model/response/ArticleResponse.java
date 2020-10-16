package com.nvlp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private List<Article> result = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<Article> getResult() {
        return result;
    }

    public void setResult(List<Article> result) {
        this.result = result;
    }


}
