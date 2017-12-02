package daluobo.insplash.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import daluobo.insplash.R;
import daluobo.insplash.adapter.CollectionsAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.model.Collection;
import daluobo.insplash.viewmodel.CollectionsViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class CollectionsFragment extends PhotosFragment {
    protected CollectionsViewModel mViewModel;
    protected CollectionsAdapter mAdapter;

    public CollectionsFragment() {
    }

    public static CollectionsFragment newInstance() {
        CollectionsFragment fragment = new CollectionsFragment();
        return fragment;
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

        super.initListView(mAdapter);

    }

    @Override
    public void onRefresh() {
        mViewModel.onRefresh().observe(this, new ResourceObserver<Resource<List<Collection>>, List<Collection>>(getContext()) {
            @Override
            protected void onSuccess(List< Collection > photos) {
                mAdapter.clearItems();
                mAdapter.addItems(photos);
                mAdapter.notifyDataSetChanged();

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
