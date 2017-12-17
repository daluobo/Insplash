package daluobo.insplash.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.BaseFragment;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.model.net.User;
import daluobo.insplash.viewmodel.UserViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class UsersFragment extends BaseFragment {
    protected UserViewModel mModel;

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
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.bio)
    TextView mBio;
    @BindView(R.id.twitter_username)
    TextView mTwitterUsername;
    @BindView(R.id.instagram_username)
    TextView mInstagramUsername;
    @BindView(R.id.extra_info_container)
    LinearLayout mExtraInfoContainer;

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

        initData();
        initView();
        return view;
    }

    @Override
    public void initData() {
        mModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mModel.getMyProfile().observe(this, new ResourceObserver<Resource<User>, User>(getContext()) {
            @Override
            protected void onSuccess(User user) {
                ImgUtil.loadImg(getContext(), mAvatar, user.profile_image.large);

                mUsername.setText(user.name);
                mTotalLikes.setText(user.total_likes + "");
                mTotalPhotos.setText(user.total_photos + "");
                mTotalCollections.setText(user.total_collections + "");

                mEmail.setText(user.email);
                mBio.setText(user.bio);
                mTwitterUsername.setText(user.twitter_username);
                mInstagramUsername.setText(user.instagram_username);
            }
        });
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.avatar, R.id.user_info_container, R.id.show_more_info_container, R.id.btn_setting, R.id.total_photos_container, R.id.total_collections_container, R.id.total_likes_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                break;
            case R.id.user_info_container:
                break;
            case R.id.show_more_info_container:
                if (mExtraInfoContainer.getVisibility() == View.VISIBLE) {
                    mExtraInfoContainer.setVisibility(View.GONE);
                } else {
                    mExtraInfoContainer.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_setting:
                break;
            case R.id.total_photos_container:
                break;
            case R.id.total_collections_container:
                break;
            case R.id.total_likes_container:
                break;
        }
    }

}
