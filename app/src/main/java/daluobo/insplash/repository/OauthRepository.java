package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.common.AppConstant;
import daluobo.insplash.model.net.Token;
import daluobo.insplash.base.arch.NetworkResource;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.OauthApi;

/**
 * Created by daluobo on 2017/11/8.
 */

public class OauthRepository extends BaseRepository{
    private OauthApi mOauthService;

    public OauthRepository(){
        mOauthService = RetrofitHelper.buildUnsplsh().create(OauthApi.class);
    }

    public LiveData<Resource<Token>> getToken(final String code) {
        return new NetworkResource<Token, Token>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                Map<String, String> param = new HashMap<>();
                param.put("client_id", AppConstant.APP_API_ID);
                param.put("client_secret", AppConstant.APP_API_SECRET);
                param.put("redirect_uri", "insplash://" + AppConstant.APP_CALLBACK_URL);
                param.put("code", code);
                param.put("grant_type", "authorization_code");

                return mOauthService.token(param);
            }

            @Override
            protected Token convertResult(@NonNull Token item) {
                return item;
            }
        }.getAsLiveData();
    }
}
