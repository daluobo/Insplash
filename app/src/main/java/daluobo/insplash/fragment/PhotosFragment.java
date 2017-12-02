package daluobo.insplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
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
    protected LayoutInflater mInflater;

    @BindView(R.id.header_container)
    FrameLayout mHeaderContainer;


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
        mInflater = LayoutInflater.from(getContext());

        mViewModel = new PhotoViewModel();
        mAdapter = new PhotosAdapter(getContext());
        mAdapter.setOnLikeClickListener(new PhotosAdapter.OnLikeClickListener() {
            @Override
            public void OnLikeClick(final ImageView imageView, final Photo photo) {

            }
        });
    }

    @Override
    public void initView() {
        View titleView = mInflater.inflate(R.layout.header_photos, null);
        mHeaderContainer.addView(titleView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        super.initListView(mAdapter);
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
    public void onLoad() {
        mViewModel.load(mViewModel.getPage()).observe(this, new ResourceObserver<Resource<List<Photo>>, List<Photo>>(getContext()) {

            @Override
            protected void onSuccess(List<Photo> photos) {
                mAdapter.addItems(photos);
                mAdapter.notifyDataSetChanged();

                mViewModel.onPageLoaded();
            }

            @Override
            protected void onFinal() {
                super.onFinal();
                mOnScrollUpListener.setLoading(false);
            }
        });
    }



}
