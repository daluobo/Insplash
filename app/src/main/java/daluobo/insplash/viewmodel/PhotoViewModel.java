package daluobo.insplash.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.model.Photo;
import daluobo.insplash.repository.PhotoRepository;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotoViewModel extends ViewModel{
    private LiveData<Resource<List<Photo>>> mPhotos;
    protected PhotoRepository mRepository;


    public PhotoViewModel(){
        mRepository = new PhotoRepository();
    }

    public LiveData<Resource<List<Photo>>> getPhotos(int page, String order_by) {
        if (this.mPhotos != null) {
            return mPhotos;
        } else {
            mPhotos = mRepository.getPhotos(page, order_by);
        }
        return mPhotos;
    }
}
