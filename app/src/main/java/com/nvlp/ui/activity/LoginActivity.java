package com.nvlp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.nvlp.R;
import com.nvlp.databinding.ActivityLoginBinding;
import com.nvlp.interfaces.ILogin;
import com.nvlp.presenter.LoginPresenter;
import com.nvlp.utils.EmailValidator;
import com.nvlp.utils.Util;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class LoginActivity extends BaseActivity implements ILogin {
    private ActivityLoginBinding binding;
    private LoginPresenter loginPresenter;
    private EmailValidator emailValidator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_login);


//presenter to call login api
        loginPresenter = new LoginPresenter();
        loginPresenter.setView(this);

        // Setup field validators.
        emailValidator = new EmailValidator(this, binding.inputEmail);
        binding.edtEmail.addTextChangedListener(emailValidator);
        binding.btnLogin.setOnClickListener(view -> {
            if (Util.validateEmail(binding.edtEmail.getText().toString())) {
                binding.inputEmail.setError(getString(R.string.entervalidemail));
            } else {
                loginPresenter.login();
            }
        });


    }


    @Override
    public void onsuccess(String token) {
        Util.hideKeyboard(this);
        Intent i = new Intent(this, ChartActivity.class);
        i.putExtra("token", token);
        startActivity(i);
        finish();

    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void onError(String reason) {
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
    }

    @Override
    public void enableLoadingBar(boolean enable) {
        enableLoader(enable);
    }


}
