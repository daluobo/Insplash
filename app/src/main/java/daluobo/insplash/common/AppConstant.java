package daluobo.insplash.common;

/**
 * Created by daluobo on 2017/8/22.
 */

public class AppConstant {

    public static final String UNSPLASH_ENDPOINT = "https://unsplash.com";
    public static final String UNSPLASH_SSL_PIN = "sha256/60J+uBsULLchqgoeQGCJeLilfJP/JWzhwUb06mXkvGM=";

    public static final String API_ENDPOINT = "https://api.unsplash.com";
    public static final String API_SSL_PIN = "sha256/60J+uBsULLchqgoeQGCJeLilfJP/JWzhwUb06mXkvGM=";

    public static final String IMAGE_ENDPOINT = "https://images.unsplash.com";
    public static final String IMAGE_SSL_PIN = "sha256/8Q7osvwwjCV7oXU8aUl1VRqE7AIlZZRKtaCJsfd5w1c=";

    public static final String APP_CALLBACK_URL = "insplash-auth-callback";
    public static final String APP_API_ID = "2a9d8042c1e107475dc0d53fc70e39e8060cbf42bbd203c9961c0a111ade8989";
    public static final String APP_API_SECRET = "e95899661a9fc20acbad9aabbe0adaad711c6ce395aa352ebc24d42b8c39b14b";

//    public static final String APP_API_ID = "7bf112dfe38b71025468053af65c1f8ebe7a7ecf286eddb86c3131deb64b2ac4";
//    public static final String APP_API_SECRET = "bc149f8b667347f502b9d10b9b48b092547a7e17762237c8f3a53b1897e6101b";

//    public static final String APP_API_ID = "41f1f23556b01d63b1ae823bdf008cc32ce446f77c843e2daa2a80c770015df3";
//    public static final String APP_API_SECRET = "dec952db51b50babd1ba55e26b7dbbd596ae03554a59c0055864826f7565c94e";

    public static String getLoginUrl() {
        return AppConstant.UNSPLASH_ENDPOINT + "/oauth/authorize"
                + "?client_id=" + AppConstant.APP_API_ID
                + "&redirect_uri=insplash://" + AppConstant.APP_CALLBACK_URL
                + "&response_type=" + "code"
                + "&scope=" + "public+read_user+write_user+read_photos+write_photos+write_likes+write_followers+read_collections+write_collections";
    }

    public static final String SHARE_PREF_KEY = "turboz_set";

    public static class SharePref {
        public static String ACCESS_TOKEN = "access_token";
        public static String REFRESH_TOKEN = "refresh_token";
        public static String USER_NAME = "user_name";
        public static String VIEW_TYPE = "view_type";
        public static String LANGUAGE = "language";
    }
}
