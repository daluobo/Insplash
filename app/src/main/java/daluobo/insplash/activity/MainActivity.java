package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.fragment.CollectionsFragment;
import daluobo.insplash.fragment.PhotosFragment;
import daluobo.insplash.fragment.UsersFragment;
import daluobo.insplash.presenter.FragmentHeapPresenter;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    protected List<Fragment> mFragments = new ArrayList<>();
    protected FragmentManager mFragmentManager;
    protected FragmentHeapPresenter mFragmentHeapPresenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String tag = item.getItemId() + "";

            if (mFragmentHeapPresenter.getFragment(tag) == null) {
                switch (item.getItemId()) {
                    case R.id.navigation_photos:
                        mFragmentHeapPresenter.addFragment(PhotosFragment.newInstance(), tag);
                        break;
                    case R.id.navigation_collections:
                        mFragmentHeapPresenter.addFragment(CollectionsFragment.newInstance(), tag);
                        break;
                    case R.id.navigation_users:
                        mFragmentHeapPresenter.addFragment(UsersFragment.newInstance(), tag);
                        break;
                }
            }

            return mFragmentHeapPresenter.showFragment(tag);
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
    }

    @Override
    public void initView() {
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
