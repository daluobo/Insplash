package daluobo.insplash.fragment;

import daluobo.insplash.base.view.SwipeListFragment;

/**
 * Created by daluobo on 2017/11/9.
 */

public class PhotosFragment extends SwipeListFragment {
    public PhotosFragment() {
    }

    public static PhotosFragment newInstance() {
        PhotosFragment fragment = new PhotosFragment();
        return fragment;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowLoading() {

    }
}
