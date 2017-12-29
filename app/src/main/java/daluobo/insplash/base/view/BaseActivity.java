package daluobo.insplash.base.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import daluobo.insplash.event.LanguageEvent;
import daluobo.insplash.helper.ConfigHelper;

/**
 * Created by daluobo on 2017/11/1.
 */

public abstract class BaseActivity extends AppCompatActivity implements IContentView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ConfigHelper.changeAppLanguage(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initContent() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLanguageEvent(LanguageEvent event){
        ConfigHelper.changeAppLanguage(this);
        recreate();
    }

}
