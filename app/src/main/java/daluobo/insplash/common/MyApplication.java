package daluobo.insplash.common;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import daluobo.insplash.util.imm.IMMLeaks;

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

        IMMLeaks.fixFocusedViewLeak(this);
        initLeakCanary();

    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}