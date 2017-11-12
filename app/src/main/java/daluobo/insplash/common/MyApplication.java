package daluobo.insplash.common;

import android.app.Application;

/**
 * Created by daluobo on 2017/10/31.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initLeakCanary();

    }

    private void initLeakCanary() {
    }
}