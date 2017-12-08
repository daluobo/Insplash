package daluobo.insplash.fragment.search;

import android.os.Bundle;

import java.util.List;

import daluobo.insplash.adapter.UsersAdapter;
import daluobo.insplash.model.User;
import daluobo.insplash.viewmodel.SearchUserViewModel;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchUserFragment extends SearchFragment<List<User>> {

    public SearchUserFragment() {
    }

    public static SearchUserFragment newInstance(String query) {
        SearchUserFragment fragment = new SearchUserFragment();

        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void initData() {
        String query = getArguments().getString(ARG_QUERY);
        mViewModel = new SearchUserViewModel(query);
        mAdapter = new UsersAdapter(getContext());
    }
}
