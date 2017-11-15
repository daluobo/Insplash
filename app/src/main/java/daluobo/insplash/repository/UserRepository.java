package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.User;
import daluobo.insplash.net.NetworkResource;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.UserApi;

/**
 * Created by daluobo on 2017/11/15.
 */

public class UserRepository {
    private UserApi mUserService;

    public UserRepository(){
        mUserService = RetrofitHelper.buildApi().create(UserApi.class);
    }

    public LiveData<Resource<User>> getUser() {
        return new NetworkResource<User, User>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return mUserService.user();
            }

            @Override
            protected User convertResult(@NonNull User item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<User>> getMe() {
        return new NetworkResource<User, User>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return mUserService.me();
            }

            @Override
            protected User convertResult(@NonNull User item) {
                return item;
            }
        }.getAsLiveData();
    }
}
