package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import daluobo.insplash.model.MonthStats;
import daluobo.insplash.model.TotalStats;
import daluobo.insplash.base.arch.ApiResponse;
import retrofit2.http.GET;

/**
 * Created by daluobo on 2017/11/1.
 */

public interface StatsApi {
    @GET("/stats/total")
    LiveData<ApiResponse<TotalStats>> total();

    @GET("/stats/month")
    LiveData<ApiResponse<MonthStats>> month();
}

