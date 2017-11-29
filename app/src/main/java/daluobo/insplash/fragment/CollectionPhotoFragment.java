package daluobo.insplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.viewmodel.CollectionsViewModel;

/**
 * Created by daluobo on 2017/11/29.
 */

public class CollectionPhotoFragment extends SwipeListFragment {
    public static final String ARG_COLLECTION = "collection";
    protected Collection mCollection;
    protected CollectionsViewModel mViewModel;
    protected PhotosAdapter mAdapter;

    public CollectionPhotoFragment() {
    }

    public static CollectionPhotoFragment newInstance(Collection collection) {
        CollectionPhotoFragment fragment = new CollectionPhotoFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_COLLECTION, collection);
        fragment.setArguments(args);

        return fragment;
    }

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
        mCollection = getArguments().getParcelable(ARG_COLLECTION);
        mViewModel = new CollectionsViewModel();
        mAdapter = new PhotosAdapter(getContext());
        mAdapter.setOnLikeClickListener(new PhotosAdapter.OnLikeClickListener() {
            @Override
            public void OnLikeClick(final ImageView imageView, final Photo photo) {

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
        mViewModel.refreshCollectionPhoto(mCollection.id + "").observe(this, new ResourceObserver<Resource<List<Photo>>, List<Photo>>(getContext()) {

            @Override
            protected void onSuccess(List<Photo> photos) {
                mAdapter.clearItems();
                mAdapter.addItems(photos);
                mAdapter.notifyDataSetChanged();

                mViewModel.onPhotoPageLoad();
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
        mViewModel.loadCollectionPhoto(mCollection.id + "").observe(this, new ResourceObserver<Resource<List<Photo>>, List<Photo>>(getContext()) {

            @Override
            protected void onSuccess(List<Photo> photos) {
                mAdapter.addItems(photos);
                mAdapter.notifyDataSetChanged();

                mViewModel.onPhotoPageLoad();
            }

            @Override
            protected void onFinal() {
                super.onFinal();
                mOnScrollUpListener.setLoading(false);
            }
        });
    }
}
