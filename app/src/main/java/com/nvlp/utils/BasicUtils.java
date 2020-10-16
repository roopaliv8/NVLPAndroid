package com.nvlp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.nvlp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BasicUtils {

    public static boolean isValidJson(String json) {
        try {
            if (json == null) {
                return false;

            }
            new JSONObject(json);
            return true;
        } catch (JSONException e) {
            return false;

        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isOnline(Activity context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.error_internet), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
