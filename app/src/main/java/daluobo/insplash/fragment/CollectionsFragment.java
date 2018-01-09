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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.CollectionsAdapter;
import daluobo.insplash.event.OptionEvent;
import daluobo.insplash.fragment.base.BaseCollectionFragment;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.helper.PopupMenuHelper;
import daluobo.insplash.model.OptionItem;
import daluobo.insplash.view.LineDecoration;
import daluobo.insplash.viewmodel.CollectionsViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class CollectionsFragment extends BaseCollectionFragment {
    private CollectionsViewModel mCollectionsViewModel;
    protected LayoutInflater mInflater;
    protected TextView mCollectionType;
    protected LineDecoration mLineDecoration;

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

        initData();
        initView();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onViewEvent(OptionEvent event) {
        if (ConfigHelper.isCompatView()) {
            mListView.addItemDecoration(mLineDecoration);
        }else {
            mListView.removeItemDecoration(mLineDecoration);
        }
        mAdapter.notifyDataSetChanged();
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
        mLineDecoration = new LineDecoration(getContext(), 0, 4, 4);
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
            mListView.addItemDecoration(mLineDecoration);
        }
    }

    private void showSelectType() {
        PopupMenuHelper.showCollectionTypeMenu(getContext(), mCollectionType, mCollectionsViewModel.getCurrentType().getValue(), mTypeClickListener);
    }

}
