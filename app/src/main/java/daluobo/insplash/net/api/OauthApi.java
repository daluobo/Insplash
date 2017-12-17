package daluobo.insplash.net.api;

import android.arch.lifecycle.LiveData;

import java.util.Map;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.model.net.Token;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by daluobo on 2017/11/8.
 */

public interface OauthApi {
    @POST("/oauth/token")
    LiveData<ApiResponse<Token>> token(@QueryMap Map<String, String> param);

}
