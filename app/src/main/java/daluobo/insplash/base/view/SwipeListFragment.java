package daluobo.insplash.base.view;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import daluobo.insplash.R;

/**
 * Created by daluobo on 2017/11/10.
 */

public abstract class SwipeListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoadingView {

    @BindView(R.id.list_view)
    protected RecyclerView mListView;
    @BindView(R.id.swipe_layout)
    protected SwipeRefreshLayout mSwipeLayout;

    protected LinearLayoutManager mLayoutManager;
    protected OnScrollUpListener mOnScrollUpListener;

    @CallSuper
    public void initListView(@NonNull RecyclerView.Adapter adapter) {
        mSwipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright),
                ContextCompat.getColor(getContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
        mSwipeLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(getContext());
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);

        mOnScrollUpListener = new OnScrollUpListener(mSwipeLayout, mLayoutManager) {
            @Override
            public void onScrollUp() {
                onLoadMore();
            }
        };

        mListView.addOnScrollListener(mOnScrollUpListener);
    }

    @Override
    public void onShowRefresh() {
        mSwipeLayout.setProgressViewOffset(false, 0, 52);
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    public void onHideRefresh() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onHideLoading() {
        ((FooterAdapter) mListView.getAdapter()).setShowFooter(false);
    }

    @Override
    public void onShowLoading() {
        mListView.smoothScrollToPosition(mListView.getAdapter().getItemCount() - 1);
    }

    public abstract void onLoadMore();
}
