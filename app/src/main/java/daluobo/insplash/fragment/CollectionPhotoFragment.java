package daluobo.insplash.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.viewmodel.CollectionPhotoViewModel;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/11/29.
 */

public class CollectionPhotoFragment extends SwipeListFragment<Photo> {
    public static final String ARG_COLLECTION = "collection";

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
        Collection collection = getArguments().getParcelable(ARG_COLLECTION);
        mViewModel = ViewModelProviders.of(this).get(CollectionPhotoViewModel.class);
        ((CollectionPhotoViewModel)mViewModel).setCollection(collection);

        mAdapter = new PhotosAdapter(getContext(), mViewModel.getData(), this, (PhotoViewModel) mViewModel, getFragmentManager());

    }

    @Override
    public void initView() {
        super.initListView();
        super.mSwipeLayout.setEnabled(false);

        onShowRefresh();
        onRefresh();
    }

}
