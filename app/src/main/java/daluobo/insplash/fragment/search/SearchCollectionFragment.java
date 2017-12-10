package daluobo.insplash.fragment.search;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import java.util.List;

import daluobo.insplash.adapter.CollectionsAdapter;
import daluobo.insplash.model.Collection;
import daluobo.insplash.viewmodel.SearchCollectionViewModel;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchCollectionFragment extends SearchFragment<List<Collection>> {

    public SearchCollectionFragment() {
    }

    public static SearchCollectionFragment newInstance(String query) {
        SearchCollectionFragment fragment = new SearchCollectionFragment();

        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void initData() {
        String query = getArguments().getString(ARG_QUERY);
        mViewModel = ViewModelProviders.of(this).get(SearchCollectionViewModel.class);
        ((SearchCollectionViewModel) mViewModel).setQuery(query);

        mAdapter = new CollectionsAdapter(getContext(), mViewModel.getData());
    }
}
