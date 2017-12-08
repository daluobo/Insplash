package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.base.view.TabFragmentAdapter;
import daluobo.insplash.fragment.search.SearchCollectionFragment;
import daluobo.insplash.fragment.search.SearchPhotoFragment;
import daluobo.insplash.fragment.search.SearchUserFragment;

public class SearchActivity extends BaseActivity {
    public static final String ARG_QUERY = "query";

    protected TabFragmentAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.query)
    EditText mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initData();
        initView();
    }


    @Override
    public void initData() {
        String query = getIntent().getStringExtra(ARG_QUERY);
        mQuery.setText(query);

        titles.add("photos");
        titles.add("collections");
        titles.add("users");

        mFragments.add(SearchPhotoFragment.newInstance(query));
        mFragments.add(SearchCollectionFragment.newInstance(query));
        mFragments.add(SearchUserFragment.newInstance(query));

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clear, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
