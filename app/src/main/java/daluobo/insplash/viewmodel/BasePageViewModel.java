package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import daluobo.insplash.base.arch.Resource;

/**
 * Created by daluobo on 2017/11/27.
 */

public abstract class BasePageViewModel<T> extends ViewModel {
    protected int mPage = 1;

    public LiveData<Resource<List<T>>> onRefresh() {
        mPage = 1;

        return loadPage(mPage);
    }

    public LiveData<Resource<List<T>>> onLoad() {
        return loadPage(mPage);
    }

    public abstract LiveData<Resource<List<T>>> loadPage(int page);

    public void onPageLoad() {
        mPage++;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

}
