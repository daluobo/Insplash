package daluobo.insplash.common;

import android.app.Application;

/**
 * Created by daluobo on 2017/10/31.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initLeakCanary();

    }

    private void initLeakCanary() {
    }
}