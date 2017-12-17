package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.repository.UserRepository;

/**
 * Created by daluobo on 2017/12/14.
 */

public class MeCollectionsViewModel extends CollectionsViewModel {
    protected UserRepository mRepository = new UserRepository();
    private Photo mPhoto;

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return mRepository.getCollections(AuthHelper.getUsername(), mPage);
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo photo) {
        mPhoto = photo;
    }
}
