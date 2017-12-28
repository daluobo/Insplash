package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.base.view.TabFragmentAdapter;
import daluobo.insplash.fragment.UserCollectionsFragment;
import daluobo.insplash.fragment.UserPhotosFragment;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.model.net.User;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;
import daluobo.insplash.viewmodel.UserPhotoViewModel;
import daluobo.insplash.viewmodel.UserViewModel;

public class UserActivity extends BaseActivity {
    public static final String ARG_USER = "user";
    public static final String ARG_SHOW_INDEX = "show_index";

    private int mShowIndex;
    private int mHintClickCount = 0;

    protected UserViewModel mViewModel;
    protected TabFragmentAdapter mAdapter;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private boolean isShowUserInfo = false;

    @BindDrawable(R.drawable.ic_mood)
    Drawable mIcMood_0;
    @BindDrawable(R.drawable.ic_mood_satisfied)
    Drawable mIcMood_1;
    @BindDrawable(R.drawable.ic_mood_neutral)
    Drawable mIcMood_2;
    @BindDrawable(R.drawable.ic_mood_dissatisfied)
    Drawable mIcMood_3;
    @BindDrawable(R.drawable.ic_mood_bad)
    Drawable mIcMood_4;
    @BindDrawable(R.drawable.ic_mood_very_dissatisfied)
    Drawable mIcMood_5;

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.badge)
    TextView mBadge;
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
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.extra_info_hint)
    ImageView mExtraInfoHint;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.mood)
    ImageView mMood;

    @BindArray(R.array.user_tabs)
    String[] mUserTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    @Override
    public void initData() {
        User user = getIntent().getParcelableExtra(ARG_USER);
        mShowIndex = getIntent().getIntExtra(ARG_SHOW_INDEX, 0);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.setUser(user);
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                ImgUtil.loadImg(UserActivity.this, mAvatar, user.profile_image.large);

                mName.setText(user.name);
                mLocation.setText(user.location);

                if (user.badge != null) {
                    mBadge.setText(user.badge);
                    mBadge.setVisibility(View.VISIBLE);
                } else {
                    mBadge.setVisibility(View.GONE);
                }

                if (user.bio != null) {
                    mBio.setText(user.bio);
                    mBio.setVisibility(View.VISIBLE);
                } else {
                    mBio.setVisibility(View.GONE);
                }
                mEmail.setText(user.email);
                mTwitterUsername.setText(user.twitter_username);
                mInstagramUsername.setText(user.instagram_username);
            }
        });

        titles.add(mUserTabs[0]);
        titles.add(mUserTabs[1]);
        titles.add(mUserTabs[2]);

        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUserData(), UserPhotoViewModel.UserPhotosType.OWN));
        mFragments.add(UserCollectionsFragment.newInstance(mViewModel.getUserData()));
        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUserData(), UserPhotoViewModel.UserPhotosType.LIKE));

        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setCurrentItem(mShowIndex, true);
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTitle.setText("@" + mViewModel.getUserData().username);
    }

    @OnClick({R.id.avatar, R.id.user_info_container, R.id.show_more_info_container, R.id.bio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                break;
            case R.id.user_info_container:
                if (isShowUserInfo) {
                    return;
                }

                isShowUserInfo = true;

                final int distanceX = DimensionUtil.dip2px(UserActivity.this, 40);
                switch (mHintClickCount) {
                    case 0:
                        mMood.setImageDrawable(mIcMood_0);
                        break;
                    case 1:
                        mMood.setImageDrawable(mIcMood_1);
                        break;
                    case 2:
                        mMood.setImageDrawable(mIcMood_2);
                        break;
                    case 3:
                        mMood.setImageDrawable(mIcMood_3);
                        break;
                    case 4:
                        mMood.setImageDrawable(mIcMood_4);
                        break;
                    case 5:
                        mMood.setImageDrawable(mIcMood_5);
                        break;
                }
                mHintClickCount++;

                mEditProfileContainer.animate().translationXBy(-distanceX).setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mEditProfileContainer.animate().translationXBy(distanceX).setListener(new AnimatorListenerAdapter() {

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        isShowUserInfo = false;
                                    }

                                }).start();
                            }
                        }, 1000);
                    }

                }).start();
                break;
            case R.id.show_more_info_container:
                int height = ViewUtil.getViewSize(mExtraInfoContainer)[1];
                if (mExtraInfoContainer.getVisibility() == View.VISIBLE) {
                    mExtraInfoHint.animate().rotation(0);

                    ValueAnimator animator = AnimHelper.createDropDown(mExtraInfoContainer, height, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mExtraInfoContainer.setVisibility(View.GONE);
                        }
                    });
                    animator.start();
                } else {
                    mExtraInfoHint.animate().rotation(180);
                    mExtraInfoContainer.setVisibility(View.VISIBLE);

                    ValueAnimator animator = AnimHelper.createDropDown(mExtraInfoContainer, 0, height);
                    animator.start();
                }
                break;

            case R.id.bio:
                if (mViewModel.getUserData().portfolio_url != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mViewModel.getUserData().portfolio_url)));
                }
                break;
        }
    }
}
