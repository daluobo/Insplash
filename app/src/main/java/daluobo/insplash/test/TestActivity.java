package daluobo.insplash.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.util.HttpsUtil;
import daluobo.insplash.util.Tls12SocketFactory;
import okhttp3.OkHttpClient;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.img)
    ImageView mImg;

    boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        if (isShow) {
            mImg.animate().scaleX(0).scaleY(0).alpha(0).start();
        } else {
            mImg.animate().scaleX(1).scaleY(1).alpha(1).start();
        }
        isShow = !isShow;
    }

    private void initHttpsClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
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
        builder.sslSocketFactory(socketFactory, new HttpsUtil.UnSafeTrustManager());
        OkHttpClient okHttpClient = builder.build();
        //OkHttpUtils.initClient(okHttpClient);
    }
}
