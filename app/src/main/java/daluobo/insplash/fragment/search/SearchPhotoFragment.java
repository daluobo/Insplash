package daluobo.insplash.fragment.search;

import android.os.Bundle;

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

    public static SearchPhotoFragment newInstance(String query) {
        SearchPhotoFragment fragment = new SearchPhotoFragment();

        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void initData() {
        String query = getArguments().getString(ARG_QUERY);
        mViewModel = new SearchPhotoViewModel(query);
        mAdapter = new PhotosAdapter(getContext());
    }

}
