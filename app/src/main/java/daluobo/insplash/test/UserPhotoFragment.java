package daluobo.insplash.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.Photo;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/12/4.
 */

public class UserPhotoFragment extends SwipeListFragment<Photo> {
    protected PhotoViewModel mViewModel;
    protected PhotosAdapter mAdapter;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        initData();
        initView();
        return view;
    }

    @Override
    public void initData() {
        mViewModel = new PhotoViewModel();
        mAdapter = new PhotosAdapter(getContext(), mViewModel.getData());
    }

    @Override
    public void initView() {
        super.initListView();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoad() {

    }
}
