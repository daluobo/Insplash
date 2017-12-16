package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.repository.CollectionsRepository;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsViewModel extends BasePageViewModel<Collection> {
    protected CollectionsRepository mRepository = new CollectionsRepository();

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return mRepository.collections(page);
    }

    public LiveData<Resource<Collection>> createCollection(Map<String, Object> param) {
        return mRepository.createCollection(param);
    }

    public LiveData<Resource<Photo>> addToCollection(String collectionId, String photoId) {
        return mRepository.addToCollection(collectionId, photoId);
    }
}
