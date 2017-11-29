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
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.fragment.CollectionPhotoFragment;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.model.Collection;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.ToastUtil;
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
        mAdapter.setOnLikeClickListener(new PhotosAdapter.OnLikeClickListener() {
            @Override
            public void OnLikeClick(final ImageView imageView, final Photo photo) {
                mViewModel.likePhoto(photo).observe(CollectionActivity.this, new ResourceObserver<Resource<Photo>, Photo>(CollectionActivity.this) {
                    @Override
                    protected void onSuccess(Photo newPhoto) {
                        photo.liked_by_user = newPhoto.liked_by_user;

                        if (newPhoto.liked_by_user) {
                            ToastUtil.showShort(CollectionActivity.this, "like" + newPhoto.liked_by_user);

                        } else {

                            ToastUtil.showShort(CollectionActivity.this, "unlike" + newPhoto.liked_by_user);
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.setTitle(mCollection.title);
        if (mCollection.description != null) {
            mDescription.setText(mCollection.description);
            mDescription.setVisibility(View.VISIBLE);
        } else {
            mDescription.setVisibility(View.GONE);
        }
        mUsername.setText(mCollection.user.username);
        ImgHelper.loadImg(this, mUserAvatar, mCollection.user.profile_image.medium);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fragment_container, CollectionPhotoFragment.newInstance(mCollection))
                .commit();
    }
}
