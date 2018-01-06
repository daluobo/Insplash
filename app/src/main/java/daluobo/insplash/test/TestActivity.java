package daluobo.insplash.test;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.TabFragmentAdapter;
import daluobo.insplash.fragment.UserCollectionsFragment;
import daluobo.insplash.fragment.UserPhotosFragment;
import daluobo.insplash.model.net.User;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.viewmodel.UserPhotoViewModel;
import daluobo.insplash.viewmodel.UserViewModel;

public class TestActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);


        User user = getIntent().getParcelableExtra(ARG_USER);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.setUser(user);
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                ImgUtil.loadImg(TestActivity.this, mAvatar, user.profile_image.large);

                mLocation.setText(user.location);
                mName.setText(user.name);
                mTitle.setText("@" + user.username);

                if (user.photos != null && user.photos.size() > 0) {
                    ImgUtil.loadImg(TestActivity.this, mBackdrop, user.photos.get(0).urls.regular);
                }
            }
        });

        titles.add("photos");
        titles.add("collections");
        titles.add("likes");

        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUser().getValue(), UserPhotoViewModel.UserPhotosType.OWN));
        mFragments.add(UserCollectionsFragment.newInstance(mViewModel.getUser().getValue()));
        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUser().getValue(), UserPhotoViewModel.UserPhotosType.LIKE));

        mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


}
