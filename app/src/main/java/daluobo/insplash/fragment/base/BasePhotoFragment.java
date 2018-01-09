package daluobo.insplash.fragment.base;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.event.PhotoChangeEvent;
import daluobo.insplash.model.net.Photo;

/**
 * Created by daluobo on 2018/1/9.
 */

public abstract class BasePhotoFragment extends SwipeListFragment<Photo> {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoChangeEvent(PhotoChangeEvent event) {
        mAdapter.onItemChanged(event.mPhoto);
    }
}
