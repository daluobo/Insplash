package daluobo.insplash.helper;

import daluobo.insplash.common.AppConstant;
import daluobo.insplash.common.MyApplication;
import daluobo.insplash.util.SharePrefUtil;

/**
 * Created by daluobo on 2017/11/16.
 */

public class SharePrefHelper {

    public static String getAccessToken() {
        return SharePrefUtil.getPreference(MyApplication.getInstance(), AppConstant.SharePref.ACCESS_TOKEN);
    }

    public static void clearAccessToken() {
        SharePrefUtil.putPreference(MyApplication.getInstance(), AppConstant.SharePref.ACCESS_TOKEN, "");
    }

    public static String getRefreshToken() {
        return SharePrefUtil.getPreference(MyApplication.getInstance(), AppConstant.SharePref.REFRESH_TOKEN);
    }

    public static String getUsername() {
        return SharePrefUtil.getPreference(MyApplication.getInstance(), AppConstant.SharePref.USER_NAME);
    }

    public static void setUsername(String username) {
        SharePrefUtil.putPreference(MyApplication.getInstance(), AppConstant.SharePref.USER_NAME, username);
    }

    public static String getViewType() {
        return SharePrefUtil.getPreference(MyApplication.getInstance(), AppConstant.SharePref.VIEW_TYPE);
    }

    public static void setViewType(String type) {
        SharePrefUtil.putPreference(MyApplication.getInstance(), AppConstant.SharePref.VIEW_TYPE, type);
    }

    public static String getLanguage() {
        return SharePrefUtil.getPreference(MyApplication.getInstance(), AppConstant.SharePref.LANGUAGE);
    }

    public static void setLanguage(String language) {
        SharePrefUtil.putPreference(MyApplication.getInstance(), AppConstant.SharePref.LANGUAGE, language);
    }
}
