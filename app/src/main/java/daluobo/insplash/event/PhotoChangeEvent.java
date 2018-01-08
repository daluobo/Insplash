package daluobo.insplash.event;

import daluobo.insplash.model.net.Photo;

/**
 * Created by daluobo on 2018/1/8.
 */

public class PhotoChangeEvent {

    public Photo mPhoto;

    public PhotoChangeEvent(Photo photo) {
        mPhoto = photo;
    }
}
