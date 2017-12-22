package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.NetworkResource;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.net.PhotoDownloadLink;
import daluobo.insplash.model.net.LikePhoto;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.PhotosApi;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotoRepository {
    private PhotosApi mPhotoService;

    public PhotoRepository() {
        mPhotoService = RetrofitHelper.buildApi().create(PhotosApi.class);
    }

    public LiveData<Resource<List<Photo>>> getPhotos(final int page, final String order_by) {
        return new NetworkResource<List<Photo>, List<Photo>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Photo>>> createCall() {
                return mPhotoService.photos(page, order_by);
            }

            @Override
            protected List<Photo> convertResult(@NonNull List<Photo> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Photo>>> getCurated(final int page) {
        return new NetworkResource<List<Photo>, List<Photo>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Photo>>> createCall() {
                return mPhotoService.curated(page);
            }

            @Override
            protected List<Photo> convertResult(@NonNull List<Photo> item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<Photo>> getPhoto(final String id) {
        return new NetworkResource<Photo, Photo>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Photo>> createCall() {
                return mPhotoService.photo(id);
            }

            @Override
            protected Photo convertResult(@NonNull Photo item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<LikePhoto>> unlike(final String id) {
        return new NetworkResource<LikePhoto, LikePhoto>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<LikePhoto>> createCall() {
                return mPhotoService.unlike(id);
            }

            @Override
            protected LikePhoto convertResult(@NonNull LikePhoto item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<LikePhoto>> like(final String id) {
        return new NetworkResource<LikePhoto, LikePhoto>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<LikePhoto>> createCall() {
                return mPhotoService.like(id);
            }

            @Override
            protected LikePhoto convertResult(@NonNull LikePhoto item) {
                return item;
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<PhotoDownloadLink>> getDownloadLink(final String id) {
        return new NetworkResource<PhotoDownloadLink, PhotoDownloadLink>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PhotoDownloadLink>> createCall() {
                return mPhotoService.getDownloadLink(id);
            }

            @Override
            protected PhotoDownloadLink convertResult(@NonNull PhotoDownloadLink item) {
                return item;
            }
        }.getAsLiveData();
    }
}
