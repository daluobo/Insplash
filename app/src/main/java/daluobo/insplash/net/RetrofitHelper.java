package daluobo.insplash.net;

import android.os.Build;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import daluobo.insplash.base.arch.LiveDataCallAdapterFactory;
import daluobo.insplash.common.AppConstant;
import daluobo.insplash.net.interceptor.AuthInterceptor;
import daluobo.insplash.net.interceptor.VersionInterceptor;
import daluobo.insplash.util.HttpsUtil;
import daluobo.insplash.util.Tls12SocketFactory;
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
        return build(AppConstant.API_ENDPOINT, AppConstant.API_SSL_PIN);
    }

    public static Retrofit buildUnsplsh() {
        return build(AppConstant.UNSPLASH_ENDPOINT, AppConstant.UNSPLASH_SSL_PIN);
    }

    public static Retrofit buildImage() {
        return build(AppConstant.IMAGE_ENDPOINT, AppConstant.IMAGE_SSL_PIN);
    }

    public static Retrofit build(String endPoint, String sslKey) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        httpClientBuilder
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new VersionInterceptor())
                .addInterceptor(new AuthInterceptor());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            httpClientBuilder.certificatePinner(new CertificatePinner.Builder()
                    .add(endPoint, sslKey)
                    .build());
        } else {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                try {
                    sslContext.init(null, null, null);
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            SSLSocketFactory socketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
            httpClientBuilder.sslSocketFactory(socketFactory, new HttpsUtil.UnSafeTrustManager());
        }

        OkHttpClient client = httpClientBuilder.build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }
}
