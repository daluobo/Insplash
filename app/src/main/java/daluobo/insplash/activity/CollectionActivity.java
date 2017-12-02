package daluobo.insplash.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.fragment.CollectionPhotoFragment;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Tag;
import daluobo.insplash.util.DateUtil;
import daluobo.insplash.viewmodel.PhotoViewModel;

public class CollectionActivity extends BaseActivity {
    public static final String ARG_COLLECTION = "Collection";

    protected PhotoViewModel mViewModel;
    protected PhotosAdapter mAdapter;

    protected Collection mCollection;
    @BindView(R.id.user_avatar)
    ImageView mUserAvatar;

    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.tags)
    TextView mTags;
    @BindView(R.id.published_at)
    TextView mPublishedAt;
    @BindView(R.id.updated_at)
    TextView mUpdatedAt;
    @BindView(R.id.title)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        initData();
        initView();
    }


    @Override
    public void initData() {
        mCollection = getIntent().getParcelableExtra(ARG_COLLECTION);

        mViewModel = new PhotoViewModel();
        mAdapter = new PhotosAdapter(this);
    }

    @Override
    public void initView() {
        mTitle.setText(mCollection.title);
        if (mCollection.description != null) {
            mDescription.setText(mCollection.description);
            mDescription.setVisibility(View.VISIBLE);
        } else {
            mDescription.setVisibility(View.GONE);
        }

        StringBuffer sb =new StringBuffer();
        for (Tag tag : mCollection.tags) {
            sb.append(tag.title+"、 ");
        }
        mTags.setText(sb);

        mPublishedAt.setText(DateUtil.GmtFormatDay(mCollection.published_at));
        mUpdatedAt.setText(DateUtil.GmtFormatDay(mCollection.updated_at));

        mUsername.setText(mCollection.user.username);
        ImgHelper.loadImg(this, mUserAvatar, mCollection.user.profile_image.medium);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, CollectionPhotoFragment.newInstance(mCollection))
                .commit();
    }
}
