package daluobo.insplash.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import daluobo.insplash.base.arch.ApiResponse;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Photo;
import daluobo.insplash.net.NetworkResource;
import daluobo.insplash.net.RetrofitHelper;
import daluobo.insplash.net.api.PhotoApi;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotoRepository {


    private PhotoApi mPhotoService;

    public PhotoRepository(){
        mPhotoService = RetrofitHelper.buildApi().create(PhotoApi.class);
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
}
