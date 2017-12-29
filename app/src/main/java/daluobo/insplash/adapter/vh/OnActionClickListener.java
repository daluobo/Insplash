package daluobo.insplash.adapter.vh;

import daluobo.insplash.model.net.Photo;

/**
 * Created by daluobo on 2017/12/29.
 */

public interface OnActionClickListener {
    void onLikeClick(Photo photo);

    void onDownloadClick(Photo photo);

    void onCollectClick(Photo photo);
}
