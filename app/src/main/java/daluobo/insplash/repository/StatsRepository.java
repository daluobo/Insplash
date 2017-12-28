package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.model.net.TotalStats;
import daluobo.insplash.base.arch.NetworkResource;
import daluobo.insplash.net.api.StatsApi;

/**
 * Created by daluobo on 2017/11/1.
 */

public class StatsRepository extends BaseRepository{
    private StatsApi mStatsService;

    public StatsRepository(){
        mStatsService = RetrofitHelper.buildApi().create(StatsApi.class);
    }

    public LiveData<Resource<TotalStats>> loadTotal() {
        return new NetworkResource<TotalStats, TotalStats>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<TotalStats>> createCall() {
                return mStatsService.total();
            }

            @Override
            protected TotalStats convertResult(@NonNull TotalStats item) {
                return item;
            }
        }.getAsLiveData();
    }
}
