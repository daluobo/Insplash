package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.NetworkResource;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.model.Search;
import daluobo.insplash.model.User;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.SearchApi;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchRepository {
    private SearchApi mService;

    public SearchRepository() {
        mService = RetrofitHelper.buildApi().create(SearchApi.class);
    }

    public LiveData<Resource<Search<Photo>>> photos(final int page, final String query) {
        return new NetworkResource<Search<Photo>, Search<Photo>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Search<Photo>>> createCall() {
                return mService.photos(page, query);
            }

            @Override
            protected Search<Photo> convertResult(@NonNull Search<Photo> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Search<Collection>>> collections(final int page, final String query) {
        return new NetworkResource<Search<Collection>, Search<Collection>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Search<Collection>>> createCall() {
                return mService.collections(page, query);
            }

            @Override
            protected Search<Collection> convertResult(@NonNull Search<Collection> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Search<User>>> users(final int page, final String query) {
        return new NetworkResource<Search<User>, Search<User>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Search<User>>> createCall() {
                return mService.users(page, query);
            }

            @Override
            protected Search<User> convertResult(@NonNull Search<User> item) {
                return item;
            }
        }.getAsLiveData();
    }
}
