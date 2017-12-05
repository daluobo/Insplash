package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Photo;
import daluobo.insplash.model.User;
import daluobo.insplash.repository.UserRepository;

/**
 * Created by daluobo on 2017/12/5.
 */

public class UserPhotoViewModel extends PhotoViewModel {
    private User mUser;
    private UserRepository mRepository;

    @UserPhotosType
    private int mType;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UserPhotosType.OWN, UserPhotosType.LIKE})
    public @interface UserPhotosType {
        int OWN = 0;
        int LIKE = 1;
    }

    public UserPhotoViewModel(User user, @UserPhotosType int type) {
        super();

        this.mUser = user;
        this.mType = type;
        mRepository = new UserRepository();
    }

    @Override
    public LiveData<Resource<List<Photo>>> loadPage(int page) {
        if (mType == UserPhotosType.OWN) {
            return mRepository.photos(mUser.username, page);
        } else {
            return mRepository.likes(mUser.username, page);
        }
    }
}
