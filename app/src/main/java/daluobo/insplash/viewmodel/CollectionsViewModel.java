package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.repository.CollectionsRepository;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsViewModel extends BasePageViewModel<Collection> {
    protected CollectionsRepository mRepository;

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
}
