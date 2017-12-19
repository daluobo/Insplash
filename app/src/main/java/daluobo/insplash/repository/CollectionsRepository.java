package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.NetworkResource;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.net.CollectPhoto;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.CollectionsApi;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsRepository {
    private CollectionsApi mCollectsService;

    public CollectionsRepository() {
        mCollectsService = RetrofitHelper.buildApi().create(CollectionsApi.class);
    }

    public LiveData<Resource<List<Collection>>> collections(final int page) {
        return new NetworkResource<List<Collection>, List<Collection>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Collection>>> createCall() {
                return mCollectsService.collections(page);
            }

            @Override
            protected List<Collection> convertResult(@NonNull List<Collection> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Collection>>> featured(final int page) {
        return new NetworkResource<List<Collection>, List<Collection>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Collection>>> createCall() {
                return mCollectsService.featured(page);
            }

            @Override
            protected List<Collection> convertResult(@NonNull List<Collection> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Collection>>> curated(final int page) {
        return new NetworkResource<List<Collection>, List<Collection>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Collection>>> createCall() {
                return mCollectsService.curated(page);
            }

            @Override
            protected List<Collection> convertResult(@NonNull List<Collection> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Collection>> collection(final String id) {
        return new NetworkResource<Collection, Collection>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Collection>> createCall() {
                return mCollectsService.collection(id);
            }

            @Override
            protected Collection convertResult(@NonNull Collection item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Photo>>> loadPhoto(final String collectionId, final int page) {
        return new NetworkResource<List<Photo>, List<Photo>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Photo>>> createCall() {
                return mCollectsService.photos(collectionId, page);
            }

            @Override
            protected List<Photo> convertResult(@NonNull List<Photo> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Collection>> createCollection(final Map<String, Object> param) {
        return new NetworkResource<Collection, Collection>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Collection>> createCall() {
                return mCollectsService.createCollection(param);
            }

            @Override
            protected Collection convertResult(@NonNull Collection item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<CollectPhoto>> addToCollection(final String collectionId, final String photoId) {
        return new NetworkResource<CollectPhoto, CollectPhoto>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<CollectPhoto>> createCall() {
                return mCollectsService.addToCollection(collectionId, collectionId, photoId);
            }

            @Override
            protected CollectPhoto convertResult(@NonNull CollectPhoto item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<CollectPhoto>> removeFromCollection(final String collectionId, final String photoId) {
        return new NetworkResource<CollectPhoto, CollectPhoto>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<CollectPhoto>> createCall() {
                return mCollectsService.removeFromCollection(collectionId, collectionId, photoId);
            }

            @Override
            protected CollectPhoto convertResult(@NonNull CollectPhoto item) {
                return item;
            }
        }.getAsLiveData();
    }
}
