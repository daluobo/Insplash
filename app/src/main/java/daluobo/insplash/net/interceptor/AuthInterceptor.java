package daluobo.insplash.net.interceptor;

import android.util.Log;

import java.io.IOException;

import daluobo.insplash.common.AppConstant;
import daluobo.insplash.helper.SharePrefHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by daluobo on 2017/11/7.
 */

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request;
        String access_token = SharePrefHelper.getAccessToken();
        String refresh_token = SharePrefHelper.getRefreshToken();

        if (access_token.equals("")) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Client-ID " + AppConstant.APP_API_ID)
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + access_token)
                    .build();

            Log.e("AuthInterceptor accessT", access_token);
            Log.e("AuthInterceptor refeshT", refresh_token);
        }


        return chain.proceed(request);
    }
}
