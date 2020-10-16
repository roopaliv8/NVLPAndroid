package com.nvlp.interfaces;

import android.app.Activity;

public interface IView {

    /*Required to implement for Activity
     * Not required to implement for Fragment
     * */

    Activity getContext();


    void onError(String reason);
    void enableLoadingBar(boolean enable);

}
