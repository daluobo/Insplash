package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.model.Search;
import daluobo.insplash.model.User;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by daluobo on 2017/12/7.
 */

public interface SearchApi {
    @GET("/search/photos")
    LiveData<ApiResponse<Search<Photo>>> photos(@Query("page") int page, @Query("query") String query);

    @GET("/search/collections")
    LiveData<ApiResponse<Search<Collection>>> collections(@Query("page") int page,@Query("query") String query);

    @GET("/search/users")
    LiveData<ApiResponse<Search<User>>> users(@Query("page") int page,@Query("query") String query);
}
