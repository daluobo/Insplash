package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.base.view.SimplePageAdapter;
import daluobo.insplash.fragment.CollectionsFragment;
import daluobo.insplash.fragment.PhotosFragment;
import daluobo.insplash.helper.NavHelper;

public class MainActivity extends BaseActivity {
    private List<Fragment> mFragments = new ArrayList<>();
    private SimplePageAdapter mAdapter;

    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;

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
    public void initData() {
        mFragments.add(PhotosFragment.newInstance());
        mFragments.add(CollectionsFragment.newInstance());
        mAdapter = new SimplePageAdapter(getSupportFragmentManager(), mFragments);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHelper.toSetting(MainActivity.this);
            }
        });


        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search_view);

        mSearchView = (SearchView) searchItem.getActionView();
        mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        //mSearchAutoComplete.setHintTextColor(ContextCompat.getColor(this, R.color.colorHint));
        //mSearchAutoComplete.setTextColor(ContextCompat.getColor(this, android.R.color.background_light));
        mSearchAutoComplete.setTextSize(16);

        LinearLayout search_edit_frame = mSearchView.findViewById(R.id.search_edit_frame);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) search_edit_frame.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        search_edit_frame.setLayoutParams(params);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                NavHelper.toSearch(MainActivity.this, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                return false;
            }
        });

        return true;
    }
}
