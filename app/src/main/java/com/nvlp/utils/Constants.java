package com.nvlp.utils;

import com.nvlp.BuildConfig;

import static com.nvlp.BuildConfig.COMMONPORT;
import static com.nvlp.BuildConfig.SSEURL;

public class Constants {
    public static final String BASEURL = "http://" + BuildConfig.BASEURL;
    public static final int REFRESHTOKENCODE = 601;
    public static final String DEMOURL = "http://" + SSEURL + ":" + COMMONPORT + "/sse";

}
