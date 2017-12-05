package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.repository.CollectionsRepository;

/**
 * Created by daluobo on 2017/12/5.
 */

public class CollectionPhotoViewModel extends PhotoViewModel {
    protected CollectionsRepository mRepository;
    protected Collection mCollection;

    public CollectionPhotoViewModel(Collection collection){
        super();

        this.mCollection = collection;
        mRepository = new CollectionsRepository();
    }

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        return mRepository.loadPhotoPage(mCollection.id + "", page);
    }
}
