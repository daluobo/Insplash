package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.helper.PopupMenuHelper;
import daluobo.insplash.model.MenuItem;
import daluobo.insplash.model.net.CollectPhoto;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.repository.CollectionsRepository;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsViewModel extends BasePageViewModel<Collection> {
    protected CollectionsRepository mRepository = new CollectionsRepository();
    protected MediatorLiveData<MenuItem> mCurrentType = new MediatorLiveData<>();

    @CollectionType
    private String mType = CollectionType.ALL;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({CollectionType.ALL, CollectionType.FEATURED, CollectionType.CURATED})
    public @interface CollectionType {
        String ALL = "all";
        String FEATURED = "featured";
        String CURATED = "curated";
    }

    public CollectionsViewModel() {
        mCurrentType.setValue(PopupMenuHelper.mCollectionType.get(0));
    }

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        if (mType.equals(CollectionType.ALL)) {
            return mRepository.collections(page);
        } else if (mType.equals(CollectionType.FEATURED)) {

            return mRepository.featured(page);
        } else if (mType.equals(CollectionType.CURATED)) {
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

    public void setCurrentType(MenuItem type) {
        mCurrentType.setValue(type);
    }

    public LiveData<MenuItem> getCurrentType() {
        return mCurrentType;
    }

    public MenuItem getCurrentTypeData() {
        return mCurrentType.getValue();
    }
}
