package daluobo.insplash.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.viewmodel.CollectionPhotoViewModel;

/**
 * Created by daluobo on 2017/11/29.
 */

public class CollectionPhotoFragment extends SwipeListFragment<Photo> {
    public static final String ARG_COLLECTION = "collection";
    protected Collection mCollection;

    protected CollectionPhotoViewModel mCollectionsViewModel;

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
        mViewModel = new CollectionPhotoViewModel(mCollection);
        mAdapter = new PhotosAdapter(getContext());

        mCollectionsViewModel = (CollectionPhotoViewModel) mViewModel;
    }

    @Override
    public void initView() {
        super.initListView();

        onShowRefresh();
        onRefresh();
    }

}
