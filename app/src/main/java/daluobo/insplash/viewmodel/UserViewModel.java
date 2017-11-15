package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.User;
import daluobo.insplash.repository.UserRepository;

/**
 * Created by daluobo on 2017/11/15.
 */

public class UserViewModel extends ViewModel{
    private LiveData<Resource<User>> mMe;
    private LiveData<Resource<User>> mUser;

    protected UserRepository mRepository;

    public UserViewModel(){
        mRepository = new UserRepository();
    }

    public LiveData<Resource<User>> getMe() {
        if (this.mUser != null) {
            return mUser;
        } else {
            mUser = mRepository.getMe();
        }

        return mUser;
    }

    public LiveData<Resource<User>> getUser() {
        if (this.mUser != null) {
            return mUser;
        } else {
            mUser = mRepository.getUser();
        }

        return mUser;
    }

}
