package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.DownloadAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.db.model.DownloadItem;
import daluobo.insplash.download.DownloadPresenter;
import daluobo.insplash.view.LineDecoration;

public class DownloadActivity extends BaseActivity {

    protected DownloadAdapter mAdapter;
//    protected Handler mDownloadProcessHandler = new Handler();
//    protected Runnable mUpdateProcess = new Runnable() {
//        @Override
//        public void run() {
//            mAdapter.notifyDataSetChanged();
//            mDownloadProcessHandler.postDelayed(this, 1000);
//        }
//    };

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
    protected void onDestroy() {
        super.onDestroy();

        //mDownloadProcessHandler.removeCallbacks(mUpdateProcess);
    }

    @Override
    public void initData() {
        mAdapter = new DownloadAdapter(this, DownloadPresenter.getInstance().getDownloadItems());

        DownloadPresenter.getInstance().setOnRecordChangeListener(new DownloadPresenter.OnRecordChangeListener() {
            @Override
            public void onChange(List<DownloadItem> downloadItems) {
                mAdapter.clearItems();
                mAdapter.addItems(downloadItems);
            }
        });
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

        //mUpdateProcess.run();
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
