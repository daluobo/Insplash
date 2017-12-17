package daluobo.insplash.fragment.search;

import android.arch.lifecycle.ViewModelProviders;

import java.util.List;

import daluobo.insplash.adapter.UsersAdapter;
import daluobo.insplash.model.net.User;
import daluobo.insplash.viewmodel.SearchUserViewModel;

/**
 * Created by daluobo on 2017/12/7.
 */

public class SearchUserFragment extends SearchFragment<List<User>> {

    public SearchUserFragment() {
    }

    public static SearchUserFragment newInstance() {
        SearchUserFragment fragment = new SearchUserFragment();

        return fragment;
    }

    @Override
    public void initData() {
        mViewModel = ViewModelProviders.of(this).get(SearchUserViewModel.class);

        mAdapter = new UsersAdapter(getContext(), mViewModel.getData());
    }
}
