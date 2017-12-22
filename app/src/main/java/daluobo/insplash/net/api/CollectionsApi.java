package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.net.CollectPhoto;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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

    @GET("/collections/{id}/photos")
    LiveData<ApiResponse<List<Photo>>> photos(@Path("id") String id, @Query("page") int page);

    @FormUrlEncoded
    @POST("/collections")
    LiveData<ApiResponse<Collection>> createCollection(@FieldMap Map<String, Object> param);

    @FormUrlEncoded
    @POST("/collections/{collection_id}/add")
    LiveData<ApiResponse<CollectPhoto>> addToCollection(@Path("collection_id") String id, @Field("collection_id") String collectionId, @Field("photo_id") String photoId);

    @DELETE("/collections/{collection_id}/remove")
    LiveData<ApiResponse<CollectPhoto>> removeFromCollection(@Path("collection_id") String id, @Query("collection_id") String collectionId, @Query("photo_id") String photoId);

    @PUT("/collections/{id}")
    LiveData<ApiResponse<Collection>> updateCollection(@Path("id") String id, @QueryMap Map<String, Object> param);

    @DELETE("/collections/{id}")
    LiveData<ApiResponse<Object>> deleteCollection(@Path("id") String id);
}
