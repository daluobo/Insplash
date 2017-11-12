package daluobo.insplash.net.interceptor;

import java.io.IOException;

import daluobo.insplash.common.AppConstant;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by daluobo on 2017/11/7.
 */

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Client-ID " + AppConstant.APP_API_ID)
                .build();
        return chain.proceed(request);
    }
}