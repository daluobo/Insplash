package daluobo.insplash.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.AppBarStateChangeListener;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.base.view.TabFragmentAdapter;
import daluobo.insplash.fragment.UserCollectionsFragment;
import daluobo.insplash.fragment.UserPhotosFragment;
import daluobo.insplash.model.net.User;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.viewmodel.UserPhotoViewModel;
import daluobo.insplash.viewmodel.UserViewModel;

public class CompatUserActivity extends BaseActivity {

    public static final String ARG_USER = "user";
    protected UserViewModel mViewModel;
    protected TabFragmentAdapter mAdapter;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.root_container)
    CoordinatorLayout mRootContainer;

    @BindColor(R.color.primary_overlay)
    int mColorPrimaryOverlay;
    @BindColor(R.color.colorPrimary)
    int mColorPrimary;
    @BindColor(R.color.colorWhite)
    int mColorWhite;
    @BindColor(R.color.colorIcon)
    int mColorIcon;

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
        User user = getIntent().getParcelableExtra(ARG_USER);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.setUser(user);
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

                if (user.photos != null && user.photos.size() > 0) {
                    Random random = new Random();
                    ImgUtil.loadImg(CompatUserActivity.this, mBackdrop, new ColorDrawable(mColorPrimary), user.photos.get(random.nextInt(user.photos.size())).urls.regular);
                }
            }
        });

        titles.add("photos");
        titles.add("collections");
        titles.add("likes");

        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUserData(), UserPhotoViewModel.UserPhotosType.OWN));
        mFragments.add(UserCollectionsFragment.newInstance(mViewModel.getUserData()));
        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUserData(), UserPhotoViewModel.UserPhotosType.LIKE));

        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
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

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
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
