package daluobo.insplash.helper;

import daluobo.insplash.model.net.User;

/**
 * Created by daluobo on 2017/12/6.
 */

public class AuthHelper {
    public static User mCurrentUser;

    public static boolean isLogin() {
        String access_token = SharePrefHelper.getAccessToken();
        if (access_token.equals("")) {
            return false;
        }
        return true;
    }

    public static String getAccessToken() {
        return SharePrefHelper.getAccessToken();
    }

    public static void logout() {
        SharePrefHelper.clearAccessToken();
    }

    public static String getUsername(){
        return SharePrefHelper.getUsername();
    }
}
