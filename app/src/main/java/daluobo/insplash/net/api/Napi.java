package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.net.TrendingFeed;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by daluobo on 2018/1/16.
 */

public interface Napi {
    @GET("/napi/feeds/home")
    LiveData<ApiResponse<TrendingFeed>> trending(@Query("after") String after);
}
