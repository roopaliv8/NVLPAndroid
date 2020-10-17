package com.nvlp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    // match email pattern
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    // Email varification
    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }
}
