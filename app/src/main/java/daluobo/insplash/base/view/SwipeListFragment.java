package daluobo.insplash.base.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import daluobo.insplash.R;

/**
 * Created by daluobo on 2017/11/10.
 */

public abstract class SwipeListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoadingView{

    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;

    private Unbinder mUnbinder;
    protected LinearLayoutManager mLayoutManager;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @CallSuper
    public void initListView(@NonNull RecyclerView.Adapter adapter) {
        mSwipeLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright),
                ContextCompat.getColor(getContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
        mSwipeLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(getContext());
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onHideLoading() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
