package daluobo.insplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.Photo;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class PhotosFragment extends SwipeListFragment {
    protected PhotoViewModel mViewModel;
    protected PhotosAdapter mAdapter;

    public PhotosFragment() {
    }

    public static PhotosFragment newInstance() {
        PhotosFragment fragment = new PhotosFragment();
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
        mViewModel = new PhotoViewModel();
        mAdapter = new PhotosAdapter(getContext());
    }

    @Override
    public void initView() {
        super.initListView(mAdapter);

        mSwipeLayout.setProgressViewOffset(true, 160, 260);
    }

    @Override
    public void onRefresh() {
        mViewModel.refresh().observe(this, new ResourceObserver<Resource<List<Photo>>, List<Photo>>(getContext()) {

            @Override
            protected void onSuccess(List<Photo> photos) {
                mAdapter.clearItems();
                mAdapter.addItems(photos);
                mAdapter.notifyDataSetChanged();

                mViewModel.onPageLoaded();
            }

            @Override
            protected void onFinal() {
                super.onFinal();
                onHideRefresh();
            }
        });
    }

    @Override
    public void onLoadMore() {
        mViewModel.load(mViewModel.getPage()).observe(this, new ResourceObserver<Resource<List<Photo>>, List<Photo>>(getContext()) {

            @Override
            protected void onSuccess(List<Photo> photos) {
                mAdapter.addItems(photos);
                mAdapter.notifyDataSetChanged();

                mViewModel.onPageLoaded();
            }
        });
    }
}
