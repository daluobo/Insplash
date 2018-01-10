package daluobo.insplash.activity;

import android.arch.lifecycle.Observer;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.activity.base.BaseUserActivity;
import daluobo.insplash.base.view.AppBarStateChangeListener;
import daluobo.insplash.model.net.User;
import daluobo.insplash.util.ImgUtil;

public class CompatUserActivity extends BaseUserActivity {


    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.badge)
    TextView mBadge;
    @BindView(R.id.bio)
    TextView mBio;
    @BindView(R.id.user_info_container)
    LinearLayout mUserInfoContainer;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.root_container)
    CoordinatorLayout mRootContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compat_user);
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
                ImgUtil.loadImg(CompatUserActivity.this, mAvatar, user.profile_image.large);

                if (user.location != null) {
                    mLocation.setVisibility(View.VISIBLE);
                    mLocation.setText(user.location);
                }
                mName.setText(user.name);
                mTitle.setText("@" + user.username);

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

                if (user.photos != null && user.photos.size() > 0) {
                    Random random = new Random();
                    ImgUtil.loadImg(CompatUserActivity.this, mBackdrop, new ColorDrawable(mColorPrimary), user.photos.get(random.nextInt(user.photos.size())).urls.regular);
                } else {
                    ImgUtil.loadImg(CompatUserActivity.this, mBackdrop, new ColorDrawable(mColorPrimary), user.profile_image.large);
                }
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {

                } else if (state == State.COLLAPSED) {
                    mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
                    mTitle.setTextColor(mColorIcon);
                } else {
                    if (mTitle.getCurrentTextColor() == mColorIcon) {
                        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
                        mTitle.setTextColor(mColorWhite);
                    }
                }
            }
        });
    }
}
