package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.User;
import daluobo.insplash.repository.UserRepository;

/**
 * Created by daluobo on 2017/12/5.
 */

public class UserCollectionViewModel extends CollectionsViewModel {
    private User mUser;
    private UserRepository mRepository= new UserRepository();

    @Override
    public LiveData<Resource<List<Collection>>> loadPage(int page) {
        return mRepository.collections(mUser.username, page);
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
