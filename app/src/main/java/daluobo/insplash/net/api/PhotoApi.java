package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.Photo;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by daluobo on 2017/11/12.
 */

public interface PhotoApi {
    @GET("/photos")
    LiveData<ApiResponse<List<Photo>>> photos(@Query("page") int page, @Query("order_by") String order_by);
}
