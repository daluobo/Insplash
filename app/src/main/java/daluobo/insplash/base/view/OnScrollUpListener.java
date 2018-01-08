package daluobo.insplash.base.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by daluobo on 2017/11/24.
 */

public abstract class OnScrollUpListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "OnScrollUpListener";

    protected LinearLayoutManager mLayoutManager;
    protected SwipeRefreshLayout mSwipeLayout;

    protected boolean mIsScrollDown = false;
    protected boolean mIsLoading = false;
    protected int mLastVisibleItem;

    public OnScrollUpListener(SwipeRefreshLayout swipeLayout,
                              LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
        this.mSwipeLayout = swipeLayout;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

        if (dy > 0) {
            mIsScrollDown = true;
        } else {
            mIsScrollDown = false;
        }

        //防止RecyclerView没有滑动到顶部，触发了SwipeRefreshLayout的刷新事件
        int topRowVerticalPosition =
                (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
        mSwipeLayout.setEnabled(topRowVerticalPosition >= 0);

    }

    @Override
    public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_IDLE
                &&((LoadableAdapter) recyclerView.getAdapter()).isShowFooter
                && mIsScrollDown
                && mLastVisibleItem + 1 == mLayoutManager.getItemCount()) {

            if (mSwipeLayout.isRefreshing() || mIsLoading) {
                Log.d(TAG, "加载中,忽略操作");
                return;
            }
            mIsLoading = true;
            onScrollUp();
        }
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
    }

    public abstract void onScrollUp();
}