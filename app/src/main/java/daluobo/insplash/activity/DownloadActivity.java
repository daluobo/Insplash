package daluobo.insplash.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.DownloadAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.download.DownloadManager;
import daluobo.insplash.view.LineDecoration;

public class DownloadActivity extends BaseActivity {

    protected DownloadAdapter mAdapter;

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
        mAdapter = new DownloadAdapter(this, DownloadManager.getInstance().mDownloadItems);
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

    Handler handler = new Handler();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                    handler.postDelayed(this, 1000);
                }
            };
            runnable.run();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
