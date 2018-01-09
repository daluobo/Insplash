package daluobo.insplash.fragment.search;

import android.arch.lifecycle.ViewModelProviders;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.event.PhotoChangeEvent;
import daluobo.insplash.fragment.base.SearchFragment;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.viewmodel.PhotoViewModel;
import daluobo.insplash.viewmodel.SearchPhotoViewModel;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchPhotoFragment extends SearchFragment<Photo> {

    public SearchPhotoFragment() {
    }

    public static SearchPhotoFragment newInstance() {
        SearchPhotoFragment fragment = new SearchPhotoFragment();
        return fragment;
    }

    @Override
    public void initData() {
        mViewModel = ViewModelProviders.of(this).get(SearchPhotoViewModel.class);
        mAdapter = new PhotosAdapter(getContext(), mViewModel.getData(), this, (PhotoViewModel) mViewModel, getFragmentManager());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoChangeEvent(PhotoChangeEvent event) {
        mAdapter.onItemChanged(event.mPhoto);
    }
}
