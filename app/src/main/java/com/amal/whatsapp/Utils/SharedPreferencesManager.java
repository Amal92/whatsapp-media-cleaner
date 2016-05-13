package com.amal.whatsapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.amal.whatsapp.Applications.Whatyclean;

/**
 * Created by amal on 13/05/16.
 */
public class SharedPreferencesManager {
    private static final String PREFERENCE_NAME = "HectorPreferences";
    public static final String NOTIFICATION_PREFERENCE = "com.amal.whatsyclean.user_notification_pref";


    public static void setStringPreferenceData(String key, String value) {
        SharedPreferences.Editor editor = Whatyclean.applicationContext
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setBooleanPreference(String key, boolean value) {
        SharedPreferences.Editor editor = Whatyclean.applicationContext
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getStringPreference(String key, String defaultValue) {
        SharedPreferences preferences = Whatyclean.applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static boolean getBooleanPreference(String key, boolean defaultValue) {
        SharedPreferences preferences = Whatyclean.applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }
}
