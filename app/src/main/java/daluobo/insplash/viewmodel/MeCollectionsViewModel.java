package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.model.Collection;
import daluobo.insplash.repository.UserRepository;

/**
 * Created by daluobo on 2017/12/14.
 */

public class MeCollectionsViewModel extends BasePageViewModel<Collection> {
    protected UserRepository mRepository = new UserRepository();

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return mRepository.getCollections(AuthHelper.getUsername(), mPage);
    }
}
