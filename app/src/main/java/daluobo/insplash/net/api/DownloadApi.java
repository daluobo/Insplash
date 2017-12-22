package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import daluobo.insplash.base.arch.ApiResponse;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by daluobo on 2017/12/22.
 */

public interface DownloadApi {

    @GET
    @Streaming
    LiveData<ApiResponse<ResponseBody>> downloadWithUrl(@Url String url);
}
