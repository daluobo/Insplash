package daluobo.insplash.net;

import java.util.concurrent.TimeUnit;

import daluobo.insplash.base.arch.LiveDataCallAdapterFactory;
import daluobo.insplash.common.AppConstant;
import daluobo.insplash.net.interceptor.AuthInterceptor;
import daluobo.insplash.net.interceptor.VersionInterceptor;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daluobo on 2017/11/1.
 */

public class RetrofitHelper {
    public static final int DEFAULT_TIMEOUT = 3;

    public static Retrofit buildApi() {
        return build(AppConstant.API_ENDPOINT, AppConstant.API_SSL_PUBLIC_KEY);
    }

    public static Retrofit buildUnsplsh() {
        return build(AppConstant.UNSPLASH_ENDPOINT, AppConstant.UNSPLASH_SSL_PUBLIC_KEY);
    }

    public static Retrofit build(String endPoint, String sslKey) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        OkHttpClient client = httpClientBuilder
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new VersionInterceptor())
                .addInterceptor(new AuthInterceptor())
                .certificatePinner(new CertificatePinner.Builder()
                        .add(endPoint, sslKey)
                        .build())
                .build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }
}
