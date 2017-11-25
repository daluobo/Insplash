package daluobo.insplash.base.view;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by daluobo on 2017/11/1.
 */

public abstract class BaseFragment extends Fragment implements IContentView{
    protected Unbinder mUnbinder;

    @Override
    public void initContent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
