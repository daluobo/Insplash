package daluobo.insplash.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import daluobo.insplash.fragment.search.SearchFragment;
import daluobo.insplash.fragment.search.SearchPhotoFragment;
import daluobo.insplash.fragment.search.SearchUserFragment;
import daluobo.insplash.viewmodel.SearchViewModel;

public class SearchActivity extends BaseActivity {

    protected TabFragmentAdapter mAdapter;
    protected List<Fragment> mFragments = new ArrayList<>();
    protected List<String> titles = new ArrayList<>();
    protected SearchViewModel mViewModel;

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
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        titles.add("photos");
        titles.add("collections");
        titles.add("users");

        mFragments.add(SearchPhotoFragment.newInstance());
        mFragments.add(SearchCollectionFragment.newInstance());
        mFragments.add(SearchUserFragment.newInstance());

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

        mQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                doSearch(s.toString());
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SearchFragment sf = ((SearchFragment) mFragments.get(mViewPager.getCurrentItem()));
                if (mViewModel.getQueryData() != null && !mViewModel.getQueryData().equals(sf.getQuery())) {
                    sf.setQuery(mViewModel.getQueryData());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewModel.getQuery().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                SearchFragment sf = ((SearchFragment) mFragments.get(mViewPager.getCurrentItem()));
                sf.setQuery(s);
            }
        });
    }

    private void doSearch(String query) {
        SearchFragment sf = ((SearchFragment) (mFragments.get(mViewPager.getCurrentItem())));
        if (query.length() == 0 || query.equals(sf.getQuery())) {
            return;
        }

        mViewModel.setQuery(query);
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
            mQuery.setText("");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
