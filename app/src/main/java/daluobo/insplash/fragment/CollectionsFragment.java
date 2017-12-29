package daluobo.insplash.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.CollectionsAdapter;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.event.ViewEvent;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.helper.PopupMenuHelper;
import daluobo.insplash.model.OptionItem;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.view.LineDecoration;
import daluobo.insplash.viewmodel.CollectionsViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class CollectionsFragment extends SwipeListFragment<Collection> {
    protected LayoutInflater mInflater;
    private TextView mCollectionType;
    private CollectionsViewModel mCollectionsViewModel;

    @BindView(R.id.header_container)
    FrameLayout mHeaderContainer;

    protected PopupMenuHelper.OnMenuItemClickListener mTypeClickListener = new PopupMenuHelper.OnMenuItemClickListener() {
        @Override
        public void onItemClick(OptionItem menuItem) {
            mCollectionsViewModel.setCurrentType(menuItem);
        }

        @Override
        public void onDismiss() {

        }
    };

    public CollectionsFragment() {
    }

    public static CollectionsFragment newInstance() {
        CollectionsFragment fragment = new CollectionsFragment();
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        initData();
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onViewEvent(ViewEvent event) {
        initListView();
    }

    @Override
    public void initData() {
        mInflater = LayoutInflater.from(getContext());

        mViewModel = ViewModelProviders.of(this).get(CollectionsViewModel.class);
        mCollectionsViewModel = (CollectionsViewModel) mViewModel;
        mCollectionsViewModel.getCurrentType().observe(this, new Observer<OptionItem>() {
            @Override
            public void onChanged(@Nullable OptionItem menuItem) {
                mCollectionType.setText(menuItem.title);
                ((CollectionsViewModel) mViewModel).setType(menuItem.value);

                onShowRefresh();
                onRefresh();
            }
        });

        mCollectionsViewModel.setCurrentType(PopupMenuHelper.getCollectionType().get(0));
    }

    @Override
    public void initView() {
        View titleView = mInflater.inflate(R.layout.header_collections, null);
        mHeaderContainer.addView(titleView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mCollectionType = titleView.findViewById(R.id.collection_type);
        mCollectionType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectType();
            }
        });

        initListView();
    }

    @Override
    public void initListView() {
        mAdapter = new CollectionsAdapter(getContext(), mViewModel.getData());

        super.initListView();

        if (ConfigHelper.isCompatView()) {
            mListView.addItemDecoration(new LineDecoration(getContext(), 0, 4, 4));
        }
    }

    private void showSelectType() {
        PopupMenuHelper.showCollectionTypeMenu(getContext(), mCollectionType, mCollectionsViewModel.getCurrentTypeData(), mTypeClickListener);
    }

}
