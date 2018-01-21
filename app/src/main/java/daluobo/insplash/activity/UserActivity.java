package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.activity.base.BaseUserActivity;
import daluobo.insplash.model.net.User;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;

public class UserActivity extends BaseUserActivity {
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.mood)
    ImageView mMood;
    @BindView(R.id.edit_profile_container)
    LinearLayout mEditProfileContainer;
    @BindView(R.id.user_info_container)
    RelativeLayout mUserInfoContainer;
    @BindView(R.id.badge)
    TextView mBadge;
    @BindView(R.id.bio)
    TextView mBio;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.root_container)
    CoordinatorLayout mRootContainer;
    private int mHintClickCount = 0;
    private boolean isShowUserInfo = false;

    @BindDrawable(R.drawable.ic_mood_very_satisfied)
    Drawable mIcMood_0;
    @BindDrawable(R.drawable.ic_mood)
    Drawable mIcMood_1;
    @BindDrawable(R.drawable.ic_mood_satisfied)
    Drawable mIcMood_2;
    @BindDrawable(R.drawable.ic_mood_neutral)
    Drawable mIcMood_3;
    @BindDrawable(R.drawable.ic_mood_dissatisfied)
    Drawable mIcMood_4;
    @BindDrawable(R.drawable.ic_mood_bad)
    Drawable mIcMood_5;
    @BindDrawable(R.drawable.ic_mood_very_dissatisfied)
    Drawable mIcMood_6;


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
        super.initData();

        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                ImgUtil.loadImg(UserActivity.this, mAvatar, user.profile_image.large);

                mTitle.setText("@" + user.username);
                mName.setText(user.name);
                mLocation.setText(user.location);

                if (user.badge != null) {
                    mBadge.setText(user.badge.title);
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
            }
        });
    }

    @OnClick({R.id.avatar, R.id.user_info_container, R.id.bio})
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
                    case 6:
                        mMood.setImageDrawable(mIcMood_6);
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
            case R.id.bio:
                if (mViewModel.getUser().getValue().portfolio_url != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mViewModel.getUser().getValue().portfolio_url)));
                }
                break;
        }
    }

}
