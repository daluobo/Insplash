package daluobo.insplash.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class PhotosFragment extends SwipeListFragment {
    protected PhotoViewModel mViewModel;
    protected PhotosAdapter mAdapter;

    protected int mPage = 1;
    protected String mOrderBy = "latest";
    protected List<Photo> mData = new ArrayList<>();

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

        return view;
    }

    @Override
    public void initData() {
        mViewModel = new PhotoViewModel();
        mAdapter = new PhotosAdapter(getContext(), mData);

        mViewModel.getPhotos(mPage, mOrderBy).observe(this, new Observer<Resource<List<Photo>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Photo>> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        mData.addAll(resource.data);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case ERROR:
                        ToastUtil.showShort(getContext(), resource.message);
                        break;
                    case LOADING:
                        ToastUtil.showShort(getContext(), "loading");
                        break;
                }

                onHideLoading();
            }
        });
    }

    @Override
    public void initView() {
        super.initListView(mAdapter);
    }

    @Override
    public void onRefresh() {

    }

}
