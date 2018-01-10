package daluobo.insplash.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.CollectionsAdapter;
import daluobo.insplash.fragment.base.BaseCollectionFragment;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.viewmodel.CollectionRelateVIewModel;

/**
 * Created by daluobo on 2018/1/10.
 */

public class CollectionRelateFragment extends BaseCollectionFragment{
    public static final String ARG_COLLECTION = "arg_collection";


    public CollectionRelateFragment() {
    }

    public static CollectionRelateFragment newInstance(Collection collection) {
        CollectionRelateFragment fragment = new CollectionRelateFragment();

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
        mViewModel = ViewModelProviders.of(this).get(CollectionRelateVIewModel.class);

        ((CollectionRelateVIewModel)mViewModel).setCollection(collection);

        mAdapter = new CollectionsAdapter(getContext(), mViewModel.getData());
    }

    @Override
    public void initView() {
        super.initListView();

        onShowRefresh();
        onRefresh();
    }
}
