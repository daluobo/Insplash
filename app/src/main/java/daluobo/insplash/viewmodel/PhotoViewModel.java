package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.helper.PopupMenuHelper;
import daluobo.insplash.model.MenuItem;
import daluobo.insplash.model.net.PhotoDownloadLink;
import daluobo.insplash.model.net.LikePhoto;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.repository.PhotoRepository;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotoViewModel extends BasePageViewModel<Photo> {
    protected PhotoRepository mRepository = new PhotoRepository();
    protected MediatorLiveData<MenuItem> mCurrentType = new MediatorLiveData<>();
    protected MediatorLiveData<MenuItem> mOrderByType = new MediatorLiveData<>();

    @PhotoType
    private String mType = PhotoType.ALL;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({PhotoViewModel.PhotoType.ALL, PhotoViewModel.PhotoType.CURATED})
    public @interface PhotoType {
        String ALL = "all";
        String CURATED = "curated";
    }

    public PhotoViewModel() {
        mCurrentType.setValue(PopupMenuHelper.mPhotoType.get(0));
        mOrderByType.setValue(PopupMenuHelper.mOrderBy.get(0));
    }

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        if (mType.equals(PhotoType.ALL)) {
            return getPhotos(page);
        } else {
            return getCurated(page);
        }
    }

    public LiveData<Resource<List<Photo>>> getPhotos(int page) {
        return mRepository.getPhotos(page, mOrderBy);
    }

    public LiveData<Resource<List<Photo>>> getCurated(int page) {
        return mRepository.getCurated(page);
    }

    public LiveData<Resource<Photo>> getPhoto(String id) {
        return mRepository.getPhoto(id);
    }

    public LiveData<Resource<LikePhoto>> likePhoto(Photo photo) {
        if (photo.liked_by_user) {
            return mRepository.unlike(photo.id);
        } else {
            return mRepository.like(photo.id);
        }
    }

    public LiveData<Resource<PhotoDownloadLink>> getDownloadLink(String id) {
        return mRepository.getDownloadLink(id);
    }


    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
        mPage = 1;
    }

    public void setCurrentType(MenuItem type) {
        mCurrentType.setValue(type);
    }

    public void setOrderByType(MenuItem orderBy) {
        mOrderByType.setValue(orderBy);
    }

    public LiveData<MenuItem> getCurrentType() {
        return mCurrentType;
    }

    public LiveData<MenuItem> getOrderByType() {
        return mOrderByType;
    }

    public MenuItem getCurrentTypeData() {
        return mCurrentType.getValue();
    }

    public MenuItem getOrderByTypeData() {
        return mOrderByType.getValue();
    }


}
