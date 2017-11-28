package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
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

public class PhotoViewModel extends ViewModel {
    protected PhotoRepository mRepository;

    @PhotoType
    private int mType = PhotoType.ALL;
    private int mPage = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PhotoViewModel.PhotoType.ALL, PhotoViewModel.PhotoType.CURATED})
    public @interface PhotoType {
        int ALL = 0;
        int CURATED = 1;
    }

    public PhotoViewModel() {
        mRepository = new PhotoRepository();
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

    public LiveData<Resource<List<Photo>>> refresh() {
        mPage = 1;

        return loadPage(mPage);
    }

    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        if (mType == PhotoType.ALL) {
            return mRepository.getPhotos(page);
        } else {
            return mRepository.getCurated(page);
        }
    }

    public LiveData<Resource<Photo>> likePhoto(Photo photo) {
        if(photo.liked_by_user){
           return mRepository.unlike(photo.id);
        }else {
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

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public void onPageLoaded() {
        mPage++;
    }
}
