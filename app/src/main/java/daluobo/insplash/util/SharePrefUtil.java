package daluobo.insplash.util;

import android.content.Context;
import android.content.SharedPreferences;

import daluobo.insplash.common.AppConstant;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by daluobo on 2017/3/8.
 */

public class SharePrefUtil {
    public static void putPreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(AppConstant.SHARE_PREF_KEY, MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(AppConstant.SHARE_PREF_KEY, MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
