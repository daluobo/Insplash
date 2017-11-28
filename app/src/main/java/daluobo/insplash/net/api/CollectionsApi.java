package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.Collection;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by daluobo on 2017/11/27.
 */

public interface CollectionsApi {

    @GET("/collections")
    LiveData<ApiResponse<List<Collection>>> collections(@Query("page") int page);

    @GET("/collections/featured")
    LiveData<ApiResponse<List<Collection>>> featured(@Query("page") int page);

    @GET("/collections/curated")
    LiveData<ApiResponse<List<Collection>>> curated(@Query("page") int page);

    @GET("/collections/{id}")
    LiveData<ApiResponse<Collection>> collection(@Path("id") String id);
}
