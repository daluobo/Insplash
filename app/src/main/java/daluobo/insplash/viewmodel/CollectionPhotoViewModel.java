package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.repository.CollectionsRepository;

/**
 * Created by daluobo on 2017/12/5.
 */

public class CollectionPhotoViewModel extends PhotoViewModel {
    protected CollectionsRepository mRepository = new CollectionsRepository();
    protected MediatorLiveData<Collection> mCollection = new MediatorLiveData<>();
    protected Collection mCollectionData;

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        return mRepository.loadPhoto(mCollectionData.id + "", page);
    }

    public MediatorLiveData<Collection> getCollection() {
        return mCollection;
    }

    public void setCollection(Collection collection) {
        mCollection.setValue(collection);
        mCollectionData = collection;
    }

    public Collection getCollectionData() {
        return mCollectionData;
    }
}
