package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.event.CollectionChangeEvent;
import daluobo.insplash.fragment.CollectionPhotoFragment;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Tag;
import daluobo.insplash.util.DateUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.viewmodel.CollectionPhotoViewModel;

public class CollectionActivity extends BaseActivity {
    public static final String ARG_COLLECTION = "Collection";
    public static final String ARG_REVEAL_X = "revealX";
    public static final String ARG_REVEAL_Y = "revealY";

    protected CollectionPhotoViewModel mViewModel;
    protected PhotosAdapter mAdapter;
    protected int revealX;
    protected int revealY;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.tags)
    TextView mTags;
    @BindView(R.id.published_at)
    TextView mPublishedAt;
    @BindView(R.id.updated_at)
    TextView mUpdatedAt;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.user_container)
    LinearLayout mUserContainer;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.root_container)
    CoordinatorLayout mRootContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        initData();
        initView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mRootContainer.post(new Runnable() {
                @Override
                public void run() {
                    Animator animator = AnimHelper.createReveal(mRootContainer,
                            revealX,
                            revealY,
                            false, null);
                    animator.start();
                }
            });
        }
    }

    @Override
    public void initData() {
        Collection collection = getIntent().getParcelableExtra(ARG_COLLECTION);
        revealX = getIntent().getIntExtra(ARG_REVEAL_X, 0);
        revealY = getIntent().getIntExtra(ARG_REVEAL_Y, 0);

        mViewModel = ViewModelProviders.of(this).get(CollectionPhotoViewModel.class);
        mViewModel.setCollection(collection);

        mAdapter = new PhotosAdapter(this, mViewModel.getData(), this, mViewModel, getSupportFragmentManager());
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);

        mViewModel.getCollection().observe(this, new Observer<Collection>() {
            @Override
            public void onChanged(@Nullable Collection collection) {
                mTitle.setText(collection.title);

                if (collection.description != null) {
                    mDescription.setText(collection.description);
                    mDescription.setVisibility(View.VISIBLE);
                } else {
                    mDescription.setVisibility(View.GONE);
                }

                StringBuffer sb = new StringBuffer();
                for (Tag tag : collection.tags) {
                    sb.append(tag.title + "、 ");
                }
                mTags.setText(sb);

                mPublishedAt.setText(DateUtil.GmtFormatDay(collection.published_at));
                mUpdatedAt.setText(DateUtil.GmtFormatDay(collection.updated_at));

                mUsername.setText(collection.user.username);
                ImgUtil.loadImg(CollectionActivity.this, mAvatar, collection.user.profile_image.medium);
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, CollectionPhotoFragment.newInstance(mViewModel.getCollection().getValue()))
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = AnimHelper.createReveal(mRootContainer,
                    revealX,
                    revealY,
                    true,
                    new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRootContainer.setVisibility(View.INVISIBLE);
                            finish();
                            overridePendingTransition(0, 0);
                        }

                    });
            animator.start();
        } else {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectionEvent(CollectionChangeEvent event){
        switch (event.mAction){
            case CollectionChangeEvent.Action.UPDATE:
                mViewModel.setCollection(event.mCollection);
                break;
            case CollectionChangeEvent.Action.DELETE:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AuthHelper.getUsername().equals(mViewModel.getCollection().getValue().user.username)) {
            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {
            NavHelper.editCollection(getSupportFragmentManager(), mViewModel.getCollection().getValue());
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.user_container)
    public void onViewClicked() {
        NavHelper.toUser(this, mViewModel.getCollection().getValue().user, mAvatar);
    }
}
