package daluobo.insplash.base.view;

import android.support.v4.app.Fragment;

/**
 * Created by daluobo on 2017/11/1.
 */

public abstract class BaseFragment extends Fragment implements IContentView{

    @Override
    public void onStart() {
        super.onStart();

        initData();
        initView();
        initContent();
    }

    @Override
    public void initContent() {

    }
}
