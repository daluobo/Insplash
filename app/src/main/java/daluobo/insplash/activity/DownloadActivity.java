package daluobo.insplash.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.DownloadAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.db.model.DownloadInfo;
import daluobo.insplash.view.LineDecoration;
import daluobo.insplash.viewmodel.DownloadInfoViewModel;

public class DownloadActivity extends BaseActivity {
    protected DownloadAdapter mAdapter;
    protected DownloadInfoViewModel mViewModel;
    protected List<DownloadInfo> mData = new ArrayList<>();

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_view)
    RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);

        initData();
        initView();

    }


    @Override
    public void initData() {
        mViewModel = ViewModelProviders.of(this).get(DownloadInfoViewModel.class);

        mViewModel.getAll().observe(this, new Observer<List<DownloadInfo>>() {
            @Override
            public void onChanged(@Nullable List<DownloadInfo> downloadInfo) {
                mData.clear();
                mData.addAll(downloadInfo);
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new DownloadAdapter(this, mData);
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        mTitle.setText(R.string.download);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new LineDecoration(this, 0, 4, 4));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
