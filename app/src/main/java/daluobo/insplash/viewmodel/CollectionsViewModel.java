package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.OptionItem;
import daluobo.insplash.model.net.CollectPhoto;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.repository.CollectionsRepository;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsViewModel extends BasePageViewModel<Collection> {
    protected CollectionsRepository mRepository = new CollectionsRepository();
    protected MediatorLiveData<OptionItem> mCurrentType = new MediatorLiveData<>();

    @CollectionType
    private String mType = CollectionType.ALL;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({CollectionType.ALL, CollectionType.FEATURED, CollectionType.CURATED})
    public @interface CollectionType {
        String ALL = "all";
        String FEATURED = "featured";
        String CURATED = "curated";
    }

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        switch (mType) {
            case CollectionType.ALL:
                return mRepository.collections(page);
            case CollectionType.FEATURED:
                return mRepository.featured(page);
            case CollectionType.CURATED:
                return mRepository.curated(page);
        }
        return mRepository.collections(page);
    }

    public LiveData<Resource<Collection>> createCollection(Map<String, Object> param) {
        return mRepository.createCollection(param);
    }

    public LiveData<Resource<CollectPhoto>> addToCollection(String collectionId, String photoId) {
        return mRepository.addToCollection(collectionId, photoId);
    }

    public LiveData<Resource<CollectPhoto>> removeFromCollection(String collectionId, String photoId) {
        return mRepository.removeFromCollection(collectionId, photoId);
    }

    public LiveData<Resource<Collection>> updateCollection(String id, Map<String, Object> param) {
        return mRepository.updateCollection(id, param);
    }

    public LiveData<Resource<Object>> deleteCollection(String id) {
        return mRepository.deleteCollection(id);
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

    public LiveData<OptionItem> getCurrentType() {
        return mCurrentType;
    }

}
