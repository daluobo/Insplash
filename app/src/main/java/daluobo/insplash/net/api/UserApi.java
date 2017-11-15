package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.User;
import retrofit2.http.GET;

/**
 * Created by daluobo on 2017/11/15.
 */

public interface UserApi {
    @GET("/me")
    LiveData<ApiResponse<User>> me();

    @GET("/users/:username")
    LiveData<ApiResponse<User>> user();
}
