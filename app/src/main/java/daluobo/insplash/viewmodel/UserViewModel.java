package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.User;
import daluobo.insplash.repository.UserRepository;

/**
 * Created by daluobo on 2017/11/15.
 */

public class UserViewModel extends ViewModel {
    private MediatorLiveData<User> mUser = new MediatorLiveData<>();

    private User mUserData;

    protected UserRepository mRepository;

    public UserViewModel() {
        mRepository = new UserRepository();
    }

    public LiveData<Resource<User>> getMyProfile() {
        return mRepository.getMe();
    }

    public void setUser(User user) {
        mUser.setValue(user);
        mUserData = user;
    }

    public LiveData<User> getUser() {
        return mUser;
    }

    public User getUserData() {
        return mUserData;
    }

    public void setUserData(User userData) {
        mUserData = userData;
    }
}
