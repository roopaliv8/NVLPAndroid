package com.nvlp.api;

public class ApiException extends Exception {

    private String mErrorMessage;

    ApiException(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }

    @Override
    public String toString() {
        return mErrorMessage;
    }

    @Override
    public String getMessage() {
        return mErrorMessage;
    }
}
