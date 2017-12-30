package daluobo.insplash.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.model.DownloadItem;
import daluobo.insplash.service.DownloadBinder;
import daluobo.insplash.service.DownloadService;
import daluobo.insplash.view.LineDecoration;

public class DownloadActivity extends BaseActivity {

    protected DownloadAdapter mAdapter;
    List<DownloadItem> mData = new ArrayList<>();

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
        initService();
    }

    @Override
    public void initData() {


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
        if (AuthHelper.isLogin()) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        }

        return true;
    }

    /*********************************************************************************************/

    private int mDownCount = 0;
    private DownloadBinder downloadBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void initService() {
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            if (downloadBinder == null) {
                return super.onOptionsItemSelected(item);
            }

            DownloadItem di = new DownloadItem();
            di.name = "download_" + mDownCount;
            di.url = "http://www.gamersky.com/showimage/id_gamersky.shtml?http://img1.gamersky.com/image2017/12/20171223_zl_91_4/gamersky_04origin_07_2017122318267CE.jpg";
            downloadBinder.startDownload(di);

            mData.add(0, di);
            mAdapter.notifyItemChanged(0);
            mDownCount++;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
