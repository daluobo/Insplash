package daluobo.insplash.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.OptionItem;
import daluobo.insplash.model.net.LikePhoto;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.model.net.PhotoDownloadLink;
import daluobo.insplash.model.net.TrendingFeed;
import daluobo.insplash.repository.PhotoRepository;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotoViewModel extends BasePageViewModel<Photo> {
    protected PhotoRepository mRepository = new PhotoRepository();
    protected MediatorLiveData<OptionItem> mCurrentType = new MediatorLiveData<>();
    protected MediatorLiveData<OptionItem> mOrderByType = new MediatorLiveData<>();

    private String mNextTrendingPage = "";

    @PhotoType
    private String mType = PhotoType.NEW;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({PhotoType.NEW, PhotoType.CURATED, PhotoType.TRENDING})
    public @interface PhotoType {
        String NEW = "new";
        String CURATED = "curated";
        String TRENDING = "trending";
    }

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        if (mType.equals(PhotoType.NEW)) {
            return mRepository.getPhotos(page, mOrderBy);
        } else if (mType.equals(PhotoType.CURATED)) {
            return mRepository.getCurated(page);
        } else {
            if (page <= 0) {
                mNextTrendingPage = "";
            }
            return Transformations.switchMap(mRepository.getTrending(mNextTrendingPage),
                    new Function<Resource<TrendingFeed>, LiveData<Resource<List<Photo>>>>() {
                        @Override
                        public LiveData<Resource<List<Photo>>> apply(Resource<TrendingFeed> input) {
                            mNextTrendingPage = input.data.next_page;

                            MediatorLiveData<Resource<List<Photo>>> result = new MediatorLiveData<>();
                            result.setValue(new Resource(input.status, input.data.photos, input.message));
                            return result;
                        }
                    });
        }
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

    public LiveData<Resource<Photo>> getRandom(Map<String, Object> param) {
        return mRepository.getRandom(param);
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
        mPage = 1;
    }

    public void setCurrentType(OptionItem type) {
        mCurrentType.setValue(type);
    }

    public void setOrderByType(OptionItem orderBy) {
        mOrderByType.setValue(orderBy);
    }

    public LiveData<OptionItem> getCurrentType() {
        return mCurrentType;
    }

    public LiveData<OptionItem> getOrderByType() {
        return mOrderByType;
    }

    public OptionItem getCurrentTypeData() {
        return mCurrentType.getValue();
    }

    public OptionItem getOrderByTypeData() {
        return mOrderByType.getValue();
    }


}
