package co.fouani.healthinfo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import co.fouani.healthinfo.R;

public class CachingHelper {
    public static String FIRST_OPEN = "first_open";
    private static SharedPreferences prefs;

    public static SharedPreferences initialize(Context context) {
        if (prefs == null) {
            CachingHelper.prefs = context.getSharedPreferences(context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        }
        return prefs;
    }

    public static boolean getBoolean(String key, boolean defaultVal, Context context) {
        return initialize(context).getBoolean(key, defaultVal);
    }

    public static void setBoolean(String key, boolean value, Context context) {
        SharedPreferences.Editor editor = initialize(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

}
