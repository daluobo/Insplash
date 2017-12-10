package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Photo;
import daluobo.insplash.repository.PhotoRepository;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotoViewModel extends BasePageViewModel<Photo> {
    protected PhotoRepository mRepository = new PhotoRepository();

    @PhotoType
    private int mType = PhotoType.ALL;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PhotoViewModel.PhotoType.ALL, PhotoViewModel.PhotoType.CURATED})
    public @interface PhotoType {
        int ALL = 0;
        int CURATED = 1;
    }

    public PhotoViewModel() {
    }

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        if (mType == PhotoType.ALL) {
            return getPhotos(page);
        } else {
            return getCurated(page);
        }
    }

    public LiveData<Resource<List<Photo>>> getPhotos(int page) {
        return mRepository.getPhotos(page);
    }

    public LiveData<Resource<List<Photo>>> getCurated(int page) {
        return mRepository.getCurated(page);
    }

    public LiveData<Resource<Photo>> getPhoto(String id) {
        return mRepository.getPhoto(id);
    }

    public LiveData<Resource<Photo>> likePhoto(Photo photo) {
        if (photo.liked_by_user) {
            return mRepository.unlike(photo.id);
        } else {
            return mRepository.like(photo.id);
        }
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
        mPage = 1;
    }

}
