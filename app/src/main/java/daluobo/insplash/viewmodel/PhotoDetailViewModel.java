package daluobo.insplash.viewmodel;

import android.arch.lifecycle.MediatorLiveData;

import daluobo.insplash.model.net.Photo;

/**
 * Created by daluobo on 2017/12/17.
 */

public class PhotoDetailViewModel extends PhotoViewModel {
    protected MediatorLiveData<Photo> mPhoto = new MediatorLiveData<>();


    public MediatorLiveData<Photo> getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo photo) {
        mPhoto.setValue(photo);
    }
}
