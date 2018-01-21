package daluobo.insplash.activity.base;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindView;
import daluobo.insplash.R;
import daluobo.insplash.adapter.CountTabFragmentAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.fragment.UserCollectionsFragment;
import daluobo.insplash.fragment.UserPhotosFragment;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.User;
import daluobo.insplash.viewmodel.UserPhotoViewModel;
import daluobo.insplash.viewmodel.UserViewModel;

/**
 * Created by daluobo on 2017/12/30.
 */

public abstract class BaseUserActivity extends BaseActivity {
    public static final String ARG_USER = "user";
    public static final String ARG_SHOW_INDEX = "show_index";

    protected UserViewModel mViewModel;
    protected CountTabFragmentAdapter mAdapter;

    protected List<Fragment> mFragments = new ArrayList<>();
    protected List<String> titles = new ArrayList<>();
    private List<String> mCounts = new ArrayList<>();

    private int mShowIndex = 0;

    @BindArray(R.array.user_tabs)
    protected String[] mUserTabs;
    @BindColor(R.color.colorTitle)
    protected int mColorTitle;
    @BindColor(R.color.colorText)
    protected int mColorText;
    @BindColor(R.color.primary_overlay)
    protected int mColorPrimaryOverlay;
    @BindColor(R.color.colorPrimary)
    protected int mColorPrimary;
    @BindColor(R.color.colorWhite)
    protected int mColorWhite;
    @BindColor(R.color.colorIcon)
    protected int mColorIcon;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.title)
    protected TextView mTitle;
    @BindView(R.id.tab_layout)
    protected TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    protected ViewPager mViewPager;

    @Override
    public void initData() {
        User user = getIntent().getParcelableExtra(ARG_USER);
        mShowIndex = getIntent().getIntExtra(ARG_SHOW_INDEX, 0);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.setUser(user);

        mCounts.add(user.total_photos + "");
        mCounts.add(user.total_collections + "");
        mCounts.add(user.total_likes + "");

        titles.add(mUserTabs[0]);
        titles.add(mUserTabs[1]);
        titles.add(mUserTabs[2]);

        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUser().getValue(), UserPhotoViewModel.UserPhotosType.OWN));
        mFragments.add(UserCollectionsFragment.newInstance(mViewModel.getUser().getValue()));
        mFragments.add(UserPhotosFragment.newInstance(mViewModel.getUser().getValue(), UserPhotoViewModel.UserPhotosType.LIKE));

        mAdapter = new CountTabFragmentAdapter(this, getSupportFragmentManager(), mFragments, titles, mCounts);
    }

    @Override
    public void initView(){
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(mAdapter.getTabView(i));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mAdapter.setTabSelected(tab.getCustomView(), mColorTitle);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mAdapter.setTabUnSelected(tab.getCustomView(), mColorText);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setCurrentItem(mShowIndex, true);
        mAdapter.setTabSelected(mTabLayout.getTabAt(mShowIndex).getCustomView(), mColorTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AuthHelper.getUsername().equals(mViewModel.getUser().getValue().username)) {
            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            NavHelper.toProfile(this, mViewModel.getUser().getValue());
        }
        return super.onOptionsItemSelected(item);
    }
}
