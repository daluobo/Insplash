package daluobo.insplash.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.view.BaseFragment;
import daluobo.insplash.model.net.TotalStats;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.viewmodel.StatsViewModel;

/**
 * Created by daluobo on 2017/11/1.
 */

public class StatsFragment extends BaseFragment {
    @BindView(R.id.photos)
    TextView mPhotos;
    @BindView(R.id.downloads)
    TextView mDownloads;
    @BindView(R.id.views)
    TextView mViews;
    @BindView(R.id.likes)
    TextView mLikes;
    @BindView(R.id.photographers)
    TextView mPhotographers;
    @BindView(R.id.pixels)
    TextView mPixels;
    @BindView(R.id.downloads_per_second)
    TextView mDownloadsPerSecond;
    @BindView(R.id.views_per_second)
    TextView mViewsPerSecond;
    @BindView(R.id.developers)
    TextView mDevelopers;
    @BindView(R.id.applications)
    TextView mApplications;
    @BindView(R.id.requests)
    TextView mRequests;

    private StatsViewModel mViewModel;

    public StatsFragment() {
    }

    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this,view);

        initData();
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.getTotalStats().observe(this, new Observer<Resource<TotalStats>>() {

            @Override
            public void onChanged(@Nullable Resource<TotalStats> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        mPhotos.setText(resource.data.photos + "");
                        mDownloads.setText(resource.data.downloads + "");
                        mViews.setText(resource.data.views + "");
                        mLikes.setText(resource.data.likes + "");
                        mPhotographers.setText(resource.data.photographers + "");

                        mPixels.setText(resource.data.pixels + "");
                        mDownloadsPerSecond.setText(resource.data.downloads_per_second + "");
                        mViewsPerSecond.setText(resource.data.views_per_second + "");

                        mDevelopers.setText(resource.data.developers + "");
                        mApplications.setText(resource.data.applications + "");
                        mRequests.setText(resource.data.requests + "");
                        break;
                    case ERROR:
                        ToastUtil.showShort(getContext(), resource.message);
                        break;
                    case LOADING:
                        ToastUtil.showShort(getContext(), "loading");
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        mViewModel = new StatsViewModel();
    }

    @Override
    public void initView() {

    }

}
