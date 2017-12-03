package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.fragment.CollectionPhotoFragment;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Tag;
import daluobo.insplash.util.DateUtil;
import daluobo.insplash.viewmodel.PhotoViewModel;

public class CollectionActivity extends BaseActivity {
    public static final String ARG_COLLECTION = "Collection";
    public static final String ARG_REVEAL_X = "revealX";
    public static final String ARG_REVEAL_Y = "revealY";

    protected PhotoViewModel mViewModel;
    protected PhotosAdapter mAdapter;
    protected int revealX;
    protected int revealY;

    protected Collection mCollection;
    @BindView(R.id.avatar)
    ImageView mAvatar;

    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.toolbar)
    LinearLayout mToolbar;
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
    @BindView(R.id.user_container)
    LinearLayout mUserContainer;
    @BindView(R.id.root_container)
    CoordinatorLayout mRootContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        initData();
        initView();

        mRootContainer.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = AnimHelper.createReveal(mRootContainer,
                            revealX,
                            revealY,
                            false, null);
                    animator.start();
                }
            }
        });
    }


    @Override
    public void initData() {
        mCollection = getIntent().getParcelableExtra(ARG_COLLECTION);
        revealX = getIntent().getIntExtra(ARG_REVEAL_X, 0);
        revealY = getIntent().getIntExtra(ARG_REVEAL_Y, 0);

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

        StringBuffer sb = new StringBuffer();
        for (Tag tag : mCollection.tags) {
            sb.append(tag.title + "、 ");
        }
        mTags.setText(sb);

        mPublishedAt.setText(DateUtil.GmtFormatDay(mCollection.published_at));
        mUpdatedAt.setText(DateUtil.GmtFormatDay(mCollection.updated_at));

        mUsername.setText(mCollection.user.username);
        ImgHelper.loadImg(this, mAvatar, mCollection.user.profile_image.medium);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, CollectionPhotoFragment.newInstance(mCollection))
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

    @OnClick(R.id.user_container)
    public void onViewClicked() {
        NavHelper.toUser(this, mCollection.user, mAvatar);
    }
}
