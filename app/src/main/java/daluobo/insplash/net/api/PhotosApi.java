package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.net.PhotoDownloadLink;
import daluobo.insplash.model.net.LikePhoto;
import daluobo.insplash.model.net.Photo;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by daluobo on 2017/11/12.
 */

public interface PhotosApi {
    @GET("/photos")
    LiveData<ApiResponse<List<Photo>>> photos(@Query("page") int page,
                                              @Query("order_by") String order_by,
                                              @Query("per_page") int per_page);

    @GET("/photos/curated")
    LiveData<ApiResponse<List<Photo>>> curated(@Query("page") int page,
                                               @Query("per_page") int per_page);

    @GET("/photos/{id}")
    LiveData<ApiResponse<Photo>> photo(@Path("id") String id);

    @POST("/photos/{id}/like")
    LiveData<ApiResponse<LikePhoto>> like(@Path("id") String id);

    @DELETE("/photos/{id}/like")
    LiveData<ApiResponse<LikePhoto>> unlike(@Path("id") String id);

    @GET("/photos/{id}/download")
    LiveData<ApiResponse<PhotoDownloadLink>> getDownloadLink(@Path("id") String id);

    @GET("/photos/random")
    LiveData<ApiResponse<Photo>> getRandom(@QueryMap Map<String, Object> param);
}
