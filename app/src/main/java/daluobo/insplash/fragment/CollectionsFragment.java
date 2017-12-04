package daluobo.insplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.CollectionsAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.Collection;
import daluobo.insplash.viewmodel.CollectionsViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class CollectionsFragment extends SwipeListFragment<Collection> {
    protected LayoutInflater mInflater;

    @BindView(R.id.header_container)
    FrameLayout mHeaderContainer;

    public CollectionsFragment() {
    }

    public static CollectionsFragment newInstance() {
        CollectionsFragment fragment = new CollectionsFragment();
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        initData();
        initView();
        return view;
    }

    @Override
    public void initData() {
        mInflater = LayoutInflater.from(getContext());

        mViewModel = new CollectionsViewModel();
        mAdapter = new CollectionsAdapter(getContext());

    }

    @Override
    public void initView() {
        View titleView = mInflater.inflate(R.layout.header_collections, null);
        mHeaderContainer.addView(titleView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        super.initListView();

    }

    @Override
    public void onRefresh() {
        mViewModel.onRefresh().observe(this, new ResourceObserver<Resource<List<Collection>>, List<Collection>>(getContext()) {
            @Override
            protected void onSuccess(List< Collection > photos) {
                mAdapter.clearItems();
                mAdapter.addItems(photos);
                mAdapter.notifyDataSetChanged();
                mListView.scheduleLayoutAnimation();

                mViewModel.onPageLoad();
            }

            @Override
            protected void onFinal() {
                super.onFinal();
                onHideRefresh();
            }
        });
    }

    @Override
    public void onLoad() {
        mViewModel.onLoad().observe(this, new ResourceObserver<Resource<List<Collection>>, List<Collection>>(getContext()) {
            @Override
            protected void onSuccess(List<Collection> collections) {
                mAdapter.addItems(collections);
                mAdapter.notifyDataSetChanged();

                mViewModel.onPageLoad();
            }

            @Override
            protected void onFinal() {
                super.onFinal();
                mOnScrollUpListener.setLoading(false);
            }
        });
    }

}
