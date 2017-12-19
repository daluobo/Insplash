package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.NetworkResource;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.model.net.User;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.UserApi;

/**
 * Created by daluobo on 2017/11/15.
 */

public class UserRepository {
    private UserApi mUserService;

    public UserRepository() {
        mUserService = RetrofitHelper.buildApi().create(UserApi.class);
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

    public LiveData<Resource<User>> getUser(final String name) {
        return new NetworkResource<User, User>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return mUserService.user(name);
            }

            @Override
            protected User convertResult(@NonNull User item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Photo>>> photos(final String username, final int page) {
        return new NetworkResource<List<Photo>, List<Photo>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Photo>>> createCall() {
                return mUserService.photos(username, page);
            }

            @Override
            protected List<Photo> convertResult(@NonNull List<Photo> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Photo>>> likes(final String username, final int page) {
        return new NetworkResource<List<Photo>, List<Photo>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Photo>>> createCall() {
                return mUserService.likes(username, page);
            }

            @Override
            protected List<Photo> convertResult(@NonNull List<Photo> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Collection>>> collections(final String username, final int page, final int per_page) {
        return new NetworkResource<List<Collection>, List<Collection>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Collection>>> createCall() {
                return mUserService.collections(username, page, per_page);
            }

            @Override
            protected List<Collection> convertResult(@NonNull List<Collection> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<User>> updateProfile(final Map<String, String> user) {
        return new NetworkResource<User, User>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return mUserService.updateMe(user);
            }

            @Override
            protected User convertResult(@NonNull User item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Collection>>> getCollections(final String name, final int page, final int per_page) {
        return new NetworkResource<List<Collection>, List<Collection>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Collection>>> createCall() {
                return mUserService.collections(name, page, per_page);
            }

            @Override
            protected List<Collection> convertResult(@NonNull List<Collection> item) {
                return item;
            }
        }.getAsLiveData();
    }
}
