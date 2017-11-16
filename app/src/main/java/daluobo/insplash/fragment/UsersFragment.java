package daluobo.insplash.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.activity.LoginActivity;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.BaseFragment;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.model.User;
import daluobo.insplash.viewmodel.UserViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class UsersFragment extends BaseFragment {
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.total_likes)
    TextView mTotalLikes;
    @BindView(R.id.total_photos)
    TextView mTotalPhotos;
    @BindView(R.id.total_collections)
    TextView mTotalCollections;

    protected UserViewModel mModel;

    public UsersFragment() {
    }

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        mModel = new UserViewModel();

        mModel.getMe().observe(this, new ResourceObserver<Resource<User>, User>(getContext()) {
            @Override
            protected void onSuccess(User user) {
                ImgHelper.loadImg(getContext(), mAvatar, user.profile_image.small);

                mUsername.setText(user.name);
                mTotalLikes.setText(user.total_likes+"");
                mTotalPhotos.setText(user.total_photos+"");
                mTotalCollections.setText(user.total_collections+"");
            }
        });
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.avatar)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
