package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.repository.UserRepository;

/**
 * Created by daluobo on 2017/12/14.
 */

public class CollectionsEditViewModel extends CollectionsViewModel {
    protected UserRepository mRepository = new UserRepository();
    private Photo mPhoto;
    private MediatorLiveData<Collection> mCollection = new MediatorLiveData<>();

    public LiveData<Resource<List<Collection>>> getMyCollections() {
        return mRepository.getCollections(AuthHelper.getUsername(), 1, 999);
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo photo) {
        mPhoto = photo;
    }

    public MediatorLiveData<Collection> getCollection() {
        return mCollection;
    }

    public void setCollection(Collection collection) {
        mCollection.setValue(collection);
    }

    public Collection getCollectionData(){
        return mCollection.getValue();
    }
}
