package com.lexing360.app.lexingupdate.utils;

import android.text.TextUtils;

/**
 * Created by fenglingfeng on 2018/2/8.
 */

public class BooleanUtils {

    public static String convertToString(Boolean b) {
        if (b) {
            return "true";
        }
        return "false";
    }

    public static boolean convertToBoolean(String s) {
        boolean b = false;
        if (TextUtils.equals(s, "true") || TextUtils.equals(s, "ONE_DISAPPEAR")) {
            b = true;
        } else if (TextUtils.equals(s, "fasle") || TextUtils.equals(s, "ALL_DISAPPEAR")) {
            b = false;
        } else {

        }
        return b;
    }
}
