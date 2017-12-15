package daluobo.insplash.fragment.search;

import android.arch.lifecycle.ViewModelProviders;

import java.util.List;

import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.model.Photo;
import daluobo.insplash.viewmodel.SearchPhotoViewModel;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchPhotoFragment extends SearchFragment<List<Photo>> {

    public SearchPhotoFragment() {
    }

    public static SearchPhotoFragment newInstance() {
        SearchPhotoFragment fragment = new SearchPhotoFragment();
        return fragment;
    }

    @Override
    public void initData() {
        mViewModel = ViewModelProviders.of(this).get(SearchPhotoViewModel.class);
        mAdapter = new PhotosAdapter(getContext(), mViewModel.getData());
    }

}
