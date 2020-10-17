package com.nvlp.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;
import com.nvlp.R;

import java.util.regex.Pattern;

public class EmailValidator implements TextWatcher {

    /**
     * Email validation pattern.
     */
// validating email id
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    private boolean mIsValid = false;
    private TextInputLayout inputEmail;
    private Context context;

    public EmailValidator(Context context, TextInputLayout inputEmail) {
        this.inputEmail = inputEmail;
        this.context = context;
    }

    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidEmail(CharSequence email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Util.validateEmail(s.toString())) {
            inputEmail.setError(context.getString(R.string.entervalidemail));
        } else {
            inputEmail.setError("");
        }
    }

    @Override
    public void afterTextChanged(Editable editableText) {
        mIsValid = isValidEmail(editableText);

    }
}