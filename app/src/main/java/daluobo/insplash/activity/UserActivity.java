package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.model.User;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.viewmodel.UserViewModel;

public class UserActivity extends BaseActivity {
    public static final String ARG_USER = "user";

    protected UserViewModel mViewModel;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.user_info_container)
    RelativeLayout mUserInfoContainer;
    @BindView(R.id.show_more_info_container)
    LinearLayout mShowMoreInfoContainer;
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
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.root_container)
    CoordinatorLayout mRootContainer;
    @BindView(R.id.edit_profile_container)
    LinearLayout mEditProfileContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        initData();
        initView();

        mRootContainer.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = AnimHelper.createReveal(mRootContainer, (int) mAvatar.getX(), (int) mAvatar.getY(), false, null);
                    animator.start();
                }
            }
        });
    }

    @Override
    public void initData() {
        mViewModel = new UserViewModel();
        User user = getIntent().getParcelableExtra(ARG_USER);
        mViewModel.setUser(user);
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                ImgHelper.loadImg(UserActivity.this, mAvatar, user.profile_image.large);

                mUsername.setText(user.name);
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

    boolean isA = false;

    @OnClick({R.id.avatar, R.id.user_info_container, R.id.show_more_info_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                break;
            case R.id.user_info_container:
                if (isA) {
                    return;
                }
                isA = true;

                final int distanceX = DimensionUtil.dip2px(UserActivity.this, 40);

                mEditProfileContainer.animate().translationXBy(-distanceX).setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mEditProfileContainer.animate().translationXBy(distanceX).setListener(new AnimatorListenerAdapter() {

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        isA = false;
                                    }

                                }).start();
                            }
                        }, 1000);
                    }

                }).start();
                break;
            case R.id.show_more_info_container:
                if (mExtraInfoContainer.getVisibility() == View.VISIBLE) {
                    mExtraInfoContainer.setVisibility(View.GONE);
                } else {
                    mExtraInfoContainer.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
