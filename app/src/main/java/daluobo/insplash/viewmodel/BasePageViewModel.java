package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import daluobo.insplash.base.arch.Resource;

/**
 * Created by daluobo on 2017/11/27.
 */

public abstract class BasePageViewModel<T> extends ViewModel {
    protected final List<T> mData = new ArrayList<>();
    protected int mPage = 1;

    @OrderBy
    protected String mOrderBy = OrderBy.LATEST;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({OrderBy.LATEST, OrderBy.OLDEST, OrderBy.POPULAR})
    public @interface OrderBy {
        String LATEST = "latest";
        String OLDEST = "oldest";
        String POPULAR = "popular";
    }

    public LiveData<Resource<List<T>>> onRefresh() {
        mPage = 1;

        return loadPage(1);
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

    public List<T> getData() {
        return mData;
    }

    public String getOrderBy() {
        return mOrderBy;
    }

    public void setOrderBy(String orderBy) {
        mOrderBy = orderBy;
    }
}
