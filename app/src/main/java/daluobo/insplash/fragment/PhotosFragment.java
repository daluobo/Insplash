package daluobo.insplash.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.Photo;
import daluobo.insplash.test.TestActivity;
import daluobo.insplash.util.ToastUtil;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
        mAdapter.setOnLikeClickListener(new PhotosAdapter.OnLikeClickListener() {
            @Override
            public void OnLikeClick(final ImageView imageView, final Photo photo) {
                mViewModel.likePhoto(photo).observe(PhotosFragment.this, new ResourceObserver<Resource<Photo>, Photo>(getContext()) {
                    @Override
                    protected void onSuccess(Photo newPhoto) {
                        photo.liked_by_user = newPhoto.liked_by_user;

                        if (newPhoto.liked_by_user) {
                            ToastUtil.showShort(getContext(), "like" + newPhoto.liked_by_user);

                        } else {

                            ToastUtil.showShort(getContext(), "unlike" + newPhoto.liked_by_user);
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void initView() {
        super.initListView(mAdapter);

        onShowRefresh();
        onRefresh();
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
        mViewModel.loadPage(mViewModel.getPage()).observe(this, new ResourceObserver<Resource<List<Photo>>, List<Photo>>(getContext()) {

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

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), TestActivity.class));
    }
}
