package com.lexing360.app.lexingupdate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

/**
 *
 * @author nnv
 * @date 2017/8/17
 */

public class SPUtil {

    private static final String NAME = "update";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "passsword";
    public static final String CURRENT_VERSION = "user";
    public static final String UPDATE_VERSION = "speech";
    public static final String CHANNEL = "update";
    public static final String UPDATE_APK_URL = "url";


    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String value = sp.getString(key, null);
        return value != null ? value : null;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

}
