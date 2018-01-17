package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.net.TrendingFeed;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by daluobo on 2018/1/16.
 */

public interface Napi {
    @Headers({"x-unsplash-client: web",
            "accept-version: v1",
            "Accept: */*",
            //"Authorization: Bearer 7e32d7703eb0c4a8316a0ada0899539e3db830b3ee6f89d9151ddcee35ba617c",
            "Authorization: Client-ID c94869b36aa272dd62dfaeefed769d4115fb3189a9d1ec88ed457207747be626",
            "Referer: https://unsplash.com/",
            "Connection: keep-alive",
            "Pragma: no-cache",
            "Cache-Control: no-cache"})
    @GET("/napi/feeds/home")
    LiveData<ApiResponse<TrendingFeed>> trending(@Query("after") String after);
}
