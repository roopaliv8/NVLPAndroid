package com.nvlp.ui.activity;

import android.app.ProgressDialog;

import com.nvlp.R;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    // show or hide loader
    protected void enableLoader(boolean enable) {
        if (enable) {
            loadProgressBar();
        } else {
            dismissProgressBar();
        }
    }

    private void loadProgressBar() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.MyTheme);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.show();
        }
    }

    private void dismissProgressBar() {
        if (!isDestroyed() && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
}
