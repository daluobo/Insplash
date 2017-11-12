package daluobo.insplash.base.view;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by daluobo on 2017/11/1.
 */

public abstract class BaseActivity extends AppCompatActivity implements IContentView{

    @Override
    protected void onStart() {
        super.onStart();

        initData();
        initView();
        initContent();
    }

    @Override
    public void initContent() {

    }
}
