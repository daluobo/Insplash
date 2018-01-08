package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.base.view.SimplePageAdapter;
import daluobo.insplash.db.model.DownloadInfo;
import daluobo.insplash.fragment.CollectionsFragment;
import daluobo.insplash.fragment.PhotosFragment;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Photo;

public class MainActivity extends BaseActivity implements PhotosAdapter.OnPhotoDownloadListener {
    private List<Fragment> mFragments = new ArrayList<>();
    private SimplePageAdapter mAdapter;

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initData() {
        mFragments.add(PhotosFragment.newInstance());
        mFragments.add(CollectionsFragment.newInstance());
        mAdapter = new SimplePageAdapter(getSupportFragmentManager(), mFragments);
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float x = 0;
                float y = 0;
                try {
                    Field field = Toolbar.class.getDeclaredField("mNavButtonView");
                    field.setAccessible(true);
                    ImageButton navIcon = (ImageButton) field.get(mToolbar);

                    x = navIcon.getX() + navIcon.getWidth() / 2;
                    y = navIcon.getY() + navIcon.getHeight() / 2;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                NavHelper.toSetting(MainActivity.this, (int) x, (int) y);
            }
        });


        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            NavHelper.toSearch(this);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDownload(Photo photo, String url) {
        DownloadInfo di = new DownloadInfo(photo, url);
    }
}
