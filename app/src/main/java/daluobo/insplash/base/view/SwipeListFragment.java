package daluobo.insplash.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.viewmodel.BasePageViewModel;

/**
 * Created by daluobo on 2017/11/10.
 */

public abstract class SwipeListFragment<T> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoadingView {

    @BindView(R.id.list_view)
    protected RecyclerView mListView;
    @BindView(R.id.swipe_layout)
    protected SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.empty_hint_container)
    LinearLayout mEmptyHintContainer;
    @BindView(R.id.error_hint_container)
    LinearLayout mErrorHintContainer;

    protected LoadableAdapter mAdapter;
    protected BasePageViewModel mViewModel;
    protected LinearLayoutManager mLayoutManager;
    protected OnScrollUpListener mOnScrollUpListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void initListView() {
        mSwipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright),
                ContextCompat.getColor(getContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
        mSwipeLayout.setOnRefreshListener(this);

        LayoutAnimationController layoutAnimation = new LayoutAnimationController(AnimHelper.getAnimationSetFromBottom());
        layoutAnimation.setDelay(0.5f);
        layoutAnimation.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mListView.setLayoutAnimation(layoutAnimation);

        mLayoutManager = new LinearLayoutManager(getContext());
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(mAdapter);

        mOnScrollUpListener = new OnScrollUpListener(mSwipeLayout, mLayoutManager) {
            @Override
            public void onScrollUp() {
                onLoad();
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
        ((LoadableAdapter) mListView.getAdapter()).setShowFooter(false);
    }

    @Override
    public void onShowLoading() {
        mListView.smoothScrollToPosition(mListView.getAdapter().getItemCount() - 1);
    }

    @Override
    public void onRefresh() {
        mViewModel.onRefresh().observe(this, new ResourceObserver<Resource<List<T>>, List<T>>(getContext()) {

            @Override
            protected void onSuccess(List<T> photos) {
                onRefreshSuccess(photos);

                mViewModel.onPageLoad();
            }

            @Override
            protected void onError(String msg) {
                mErrorHintContainer.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onFinal() {
                super.onFinal();
                onHideRefresh();
            }
        });
    }

    public void onLoad() {
        mViewModel.onLoad().observe(this, new ResourceObserver<Resource<List<T>>, List<T>>(getContext()) {

            @Override
            protected void onSuccess(List<T> photos) {
                if (photos.size() == 0) {
                    mAdapter.setShowFooter(false);
                    ToastUtil.showShort(getContext(), "Nothing more!");
                    return;
                }

                onLoadSuccess(photos);
                mViewModel.onPageLoad();
            }

            @Override
            protected void onError(String msg) {
                super.onError(msg);

                mListView.smoothScrollBy(0, -DimensionUtil.dpToPx(48));
            }

            @Override
            protected void onFinal() {
                super.onFinal();
                mOnScrollUpListener.setLoading(false);
            }
        });
    }

    protected void onRefreshSuccess(List<T> data) {
        if (data.size() == 0) {
            mEmptyHintContainer.setVisibility(View.VISIBLE);
        } else {
            mErrorHintContainer.setVisibility(View.GONE);
            mEmptyHintContainer.setVisibility(View.GONE);
        }

        mAdapter.clearItems();
        mAdapter.addItems(data);
        mAdapter.notifyDataSetChanged();

        mListView.scheduleLayoutAnimation();
    }

    protected void onLoadSuccess(List<T> data) {
        mAdapter.addItems(data);

        mAdapter.notifyItemRangeInserted(mAdapter.getItemCount() - 1, data.size());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String event) {

    }
}
