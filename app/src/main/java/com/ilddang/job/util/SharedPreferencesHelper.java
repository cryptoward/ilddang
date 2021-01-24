package com.ilddang.job.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static SharedPreferencesHelper mSharedPreferencesHelper;
    public static final String PREFS_NAME = "ILDDANG_PREFS";
    public static final String SESSION_ID_KEY = "ILDDANG_SESSION_ID";

    public static SharedPreferencesHelper getInstance() {
        if (mSharedPreferencesHelper == null)
        {
            mSharedPreferencesHelper = new SharedPreferencesHelper();
        }
        return mSharedPreferencesHelper;
    }


    public void setValue(Context context, String Key, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(Key, text);

        editor.commit();
    }

    public String getValue(Context context , String Key) {
        SharedPreferences settings;
        String text = "";

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(Key, "");
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context , String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(value);
        editor.commit();
    }

}
