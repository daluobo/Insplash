package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2018/1/10.
 */

public class CollectionRelateVIewModel extends CollectionsViewModel {
    protected MediatorLiveData<Collection> mCollection = new MediatorLiveData<>();

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return mRepository.related(mCollection.getValue().id);
    }

    public void setCollection(Collection collection){
        mCollection.setValue(collection);
    }

}
