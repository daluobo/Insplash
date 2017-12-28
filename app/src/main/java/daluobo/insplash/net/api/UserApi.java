package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.model.net.User;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by daluobo on 2017/11/15.
 */

public interface UserApi {
    @GET("/me")
    LiveData<ApiResponse<User>> me();

    @PUT("/me")
    LiveData<ApiResponse<User>> updateMe(@QueryMap Map<String, String> user);

    @GET("/users/{username}")
    LiveData<ApiResponse<User>> user(@Path("username") String name);

    @GET("/users/{username}/photos")
    LiveData<ApiResponse<List<Photo>>> photos(@Path("username") String username, @Query("page") int page, @Query("per_page") int per_page);

    @GET("/users/{username}/likes")
    LiveData<ApiResponse<List<Photo>>> likes(@Path("username") String username, @Query("page") int page, @Query("per_page") int per_page);

    @GET("/users/{username}/collections")
    LiveData<ApiResponse<List<Collection>>> collections(@Path("username") String username, @Query("page") int page, @Query("per_page") int per_page);


}
