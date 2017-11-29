package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.repository.CollectionsRepository;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsViewModel extends BasePageViewModel<Collection> {
    protected CollectionsRepository mRepository;

    protected int mPhotoPage = 1;

    public CollectionsViewModel() {
        mRepository = new CollectionsRepository();
    }

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return mRepository.collections(page);
    }

    public LiveData<Resource<Collection>> collection() {
        return mRepository.collection("1435531");
    }

    public LiveData<Resource<List<Photo>>> refreshCollectionPhoto(String collectionId) {
        mPhotoPage = 1;

        return loadPhotoPage(collectionId, mPhotoPage);
    }

    public LiveData<Resource<List<Photo>>> loadCollectionPhoto(String collectionId) {
        return loadPhotoPage(collectionId, mPhotoPage);
    }

    public LiveData<Resource<List<Photo>>> loadPhotoPage(String collectionId, int page) {
        return mRepository.loadPhotoPage(collectionId, page);
    }

    public void onPhotoPageLoad(){
        mPhotoPage++;
    }
}
