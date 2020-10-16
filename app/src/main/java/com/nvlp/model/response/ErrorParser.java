package com.nvlp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorParser {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Object> result = null;
    @SerializedName("stack")
    @Expose
    private String stack;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

}
