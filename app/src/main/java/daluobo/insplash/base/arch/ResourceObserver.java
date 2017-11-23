package daluobo.insplash.base.arch;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import daluobo.insplash.util.ToastUtil;

/**
 * Created by daluobo on 2017/11/15.
 */

public abstract class ResourceObserver<T extends Resource<D>, D > implements Observer<T> {
    Context mContext;

    public ResourceObserver(Context context){
        this.mContext = context;
    }

    @Override
    public void onChanged(@Nullable T t) {
        switch (t.status) {
            case SUCCESS:
                onSuccess(t.data);
                break;
            case ERROR:
                onError(t.message);
                break;
            case LOADING:
                onLoading();
                break;
        }

        onFinal();
    }

    protected abstract void onSuccess(D d);

    protected void onError(String msg) {
        ToastUtil.showShort(mContext, msg);
    }

    protected void onLoading() {}

    protected void onFinal() {}
}
