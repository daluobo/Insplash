package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.fragment.CollectionsFragment;
import daluobo.insplash.fragment.PhotosFragment;
import daluobo.insplash.fragment.UsersFragment;
import daluobo.insplash.presenter.FragmentHeapPresenter;
import daluobo.insplash.view.TopSearchBehavior;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    @BindView(R.id.search_container)
    CardView mSearchContainer;

    protected FragmentManager mFragmentManager;
    protected FragmentHeapPresenter mFragmentHeapPresenter;
    TopSearchBehavior mTopSearchBehavior;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String tag = item.getItemId() + "";

            switch (item.getItemId()) {
                case R.id.navigation_photos:
                    mTopSearchBehavior.setHidden(mSearchContainer, false);
                    if (mFragmentHeapPresenter.getFragment(tag) == null) {
                        mFragmentHeapPresenter.addFragment(PhotosFragment.newInstance(), tag);
                    }
                    break;
                case R.id.navigation_collections:
                    mTopSearchBehavior.setHidden(mSearchContainer, false);
                    if (mFragmentHeapPresenter.getFragment(tag) == null) {
                        mFragmentHeapPresenter.addFragment(CollectionsFragment.newInstance(), tag);
                    }
                    break;
                case R.id.navigation_users:
                    mTopSearchBehavior.setHidden(mSearchContainer, true);
                    if (mFragmentHeapPresenter.getFragment(tag) == null) {
                        mFragmentHeapPresenter.addFragment(UsersFragment.newInstance(), tag);
                    }
                    break;

            }

            mFragmentHeapPresenter.showFragment(tag);

            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentHeapPresenter = new FragmentHeapPresenter(R.id.fragment_container, mFragmentManager);
        mTopSearchBehavior = TopSearchBehavior.from(mSearchContainer);
    }

    @Override
    public void initView() {
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
