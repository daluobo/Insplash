package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.fragment.CollectionRelateFragment;
import daluobo.insplash.model.net.Collection;

public class CollectionRelatedActivity extends BaseActivity {
    public static final String ARG_COLLECTION = "arg_collection";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_related);
        ButterKnife.bind(this);

        initData();
        initView();
    }


    @Override
    public void initData() {


    }

    @Override
    public void initView() {
        mTitle.setText("Relate Collection");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Collection collection = getIntent().getParcelableExtra(ARG_COLLECTION);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, CollectionRelateFragment.newInstance(collection))
                .commit();
    }
}
