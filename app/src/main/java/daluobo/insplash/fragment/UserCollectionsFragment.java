package daluobo.insplash.fragment;

import android.os.Bundle;

import java.util.List;

import daluobo.insplash.adapter.UserCollectionsAdapter;
import daluobo.insplash.base.view.SimpleSwipeListFragment;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.User;
import daluobo.insplash.viewmodel.UserCollectionViewModel;

/**
 * Created by daluobo on 2017/12/5.
 */

public class UserCollectionsFragment extends SimpleSwipeListFragment<List<Collection>> {
    public static final String ARG_USER = "user";

    public UserCollectionsFragment() {
    }

    public static UserCollectionsFragment newInstance(User user) {
        UserCollectionsFragment fragment = new UserCollectionsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void initData() {
        User user = getArguments().getParcelable(ARG_USER);

        mViewModel = new UserCollectionViewModel(user);
        mAdapter = new UserCollectionsAdapter(getContext());
    }

}
