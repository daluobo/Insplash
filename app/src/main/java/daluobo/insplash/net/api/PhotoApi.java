package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.Photo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by daluobo on 2017/11/12.
 */

public interface PhotoApi {
    @GET("/photos")
    LiveData<ApiResponse<List<Photo>>> photos(@Query("page") int page);

    @GET("/photos/curated")
    LiveData<ApiResponse<List<Photo>>> curated(@Query("page") int page);

    @GET("/photos/{id}")
    LiveData<ApiResponse<Photo>> photo(@Path("id") String id);
}
