package daluobo.insplash.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.SimpleSwipeListFragment;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.model.net.User;
import daluobo.insplash.view.LineDecoration;
import daluobo.insplash.viewmodel.PhotoViewModel;
import daluobo.insplash.viewmodel.UserPhotoViewModel;

/**
 * Created by daluobo on 2017/12/5.
 */

public class UserPhotosFragment extends SimpleSwipeListFragment<List<Photo>> {
    public static final String ARG_USER = "user";
    public static final String ARG_TYPE = "type";

    public UserPhotosFragment() {
    }

    public static UserPhotosFragment newInstance(User user, @UserPhotoViewModel.UserPhotosType int type) {
        UserPhotosFragment fragment = new UserPhotosFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void initData() {
        User user = getArguments().getParcelable(ARG_USER);
        int type = getArguments().getInt(ARG_TYPE);
        mViewModel = ViewModelProviders.of(this).get(UserPhotoViewModel.class);
        ((UserPhotoViewModel) mViewModel).setUser(user);
        ((UserPhotoViewModel) mViewModel).setUserPhotoTyp(type);

        int column = 1;
        if (ConfigHelper.isCompatView()) {
            column = 3;
        }

        if (type == UserPhotoViewModel.UserPhotosType.OWN) {
            mAdapter = new PhotosAdapter(getContext(), mViewModel.getData(), this, (PhotoViewModel) mViewModel, getFragmentManager(), false, column);
        } else {
            mAdapter = new PhotosAdapter(getContext(), mViewModel.getData(), this, (PhotoViewModel) mViewModel, getFragmentManager());
        }
    }

    @Override
    public void initView() {
        super.initView();

        if (ConfigHelper.isCompatView()
                && ((UserPhotoViewModel) mViewModel).getUserPhotoTyp() == UserPhotoViewModel.UserPhotosType.OWN) {
            mListView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            mListView.addItemDecoration(new LineDecoration(getContext(), 8, 4, 4));
        }
    }
}
