package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.User;
import daluobo.insplash.viewmodel.UserViewModel;

public class SettingActivity extends BaseActivity {
    protected UserViewModel mModel;

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.total_photos)
    TextView mTotalPhotos;
    @BindView(R.id.total_photos_container)
    LinearLayout mTotalPhotosContainer;
    @BindView(R.id.total_collections)
    TextView mTotalCollections;
    @BindView(R.id.total_collections_container)
    LinearLayout mTotalCollectionsContainer;
    @BindView(R.id.total_likes)
    TextView mTotalLikes;
    @BindView(R.id.total_likes_container)
    LinearLayout mTotalLikesContainer;
    @BindView(R.id.user_container)
    LinearLayout mUserContainer;
    @BindView(R.id.user_count_container)
    LinearLayout mUserCountContainer;
    @BindView(R.id.name)
    TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AuthHelper.isLogin()) {
            initUserProfile();
            showUserProfile();
        } else {
            hideUserProfile();
        }
        invalidateOptionsMenu();
    }

    @Override
    public void initData() {
        mModel = new UserViewModel();
    }

    @Override
    public void initView() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setText("Setting");
    }

    private void showUserProfile() {
        if (mUserContainer.getVisibility() == View.VISIBLE) {
            return;
        }

        mUserContainer.setVisibility(View.VISIBLE);
        mUserCountContainer.setVisibility(View.VISIBLE);

        int userHeight = ViewHelper.getViewSize(mUserContainer)[1];
        ValueAnimator userAnimator = AnimHelper.createDropDown(mUserContainer, 0, userHeight);
        userAnimator.setDuration(800).start();

        int countHeight = ViewHelper.getViewSize(mTotalPhotosContainer)[1];
        ValueAnimator countAnimator = AnimHelper.createDropDown(mUserCountContainer, 0, countHeight);
        countAnimator.setDuration(800).start();
    }

    private void hideUserProfile() {
        if (mUserContainer.getVisibility() == View.GONE) {
            return;
        }

        int userHeight = ViewHelper.getViewSize(mUserContainer)[1];
        ValueAnimator userAnimator = AnimHelper.createDropDown(mUserContainer, userHeight, 0);
        userAnimator.setDuration(800).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mUserContainer.setVisibility(View.GONE);
            }
        });
        userAnimator.start();

        int countHeight = ViewHelper.getViewSize(mTotalPhotosContainer)[1];
        ValueAnimator countAnimator = AnimHelper.createDropDown(mUserCountContainer, countHeight, 0);
        countAnimator
                .setDuration(800)
                .addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mUserCountContainer.setVisibility(View.GONE);
                    }
                });
        countAnimator.start();
    }

    private void initUserProfile() {
        mModel.getMyProfile().observe(this, new ResourceObserver<Resource<User>, User>(this) {
            @Override
            protected void onSuccess(User user) {
                ImgHelper.loadImg(SettingActivity.this, mAvatar, user.profile_image.large);

                mUsername.setText(user.username);
                mName.setText(user.name);
                mTotalLikes.setText(user.total_likes + "");
                mTotalPhotos.setText(user.total_photos + "");
                mTotalCollections.setText(user.total_collections + "");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (AuthHelper.isLogin()) {
            getMenuInflater().inflate(R.menu.menu_logout, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_login, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            AuthHelper.logout();
            hideUserProfile();
            invalidateOptionsMenu();
            return true;
        } else if (id == R.id.action_login) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.user_container, R.id.total_photos_container, R.id.total_collections_container, R.id.total_likes_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_container:
                break;
            case R.id.total_photos_container:
                break;
            case R.id.total_collections_container:
                break;
            case R.id.total_likes_container:
                break;
        }
    }
}
