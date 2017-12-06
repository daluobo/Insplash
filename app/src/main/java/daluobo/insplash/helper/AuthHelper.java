package daluobo.insplash.helper;

/**
 * Created by daluobo on 2017/12/6.
 */

public class AuthHelper {
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
}
