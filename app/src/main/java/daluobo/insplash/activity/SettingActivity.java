package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import daluobo.insplash.common.AppConstant;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.util.ViewUtil;
import daluobo.insplash.model.Token;
import daluobo.insplash.model.User;
import daluobo.insplash.util.SharePrefUtil;
import daluobo.insplash.viewmodel.OauthViewModel;
import daluobo.insplash.viewmodel.UserViewModel;

public class SettingActivity extends BaseActivity {
    protected UserViewModel mViewModel;
    protected OauthViewModel mOauthViewModel;

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null
                && intent.getData() != null
                && !TextUtils.isEmpty(intent.getData().getAuthority())
                && AppConstant.APP_CALLBACK_URL.equals(intent.getData().getAuthority())) {

            mOauthViewModel.getToken(intent.getData().getQueryParameter("code")).observe(this, new ResourceObserver<Resource<Token>, Token>(this) {
                @Override
                protected void onSuccess(Token token) {
                    SharePrefUtil.putPreference(SettingActivity.this,AppConstant.SharePref.ACCESS_TOKEN, token.access_token);
                    SharePrefUtil.putPreference(SettingActivity.this,AppConstant.SharePref.REFRESH_TOKEN, token.refresh_token);

                    NavHelper.toSetting(SettingActivity.this);
                }
            });
        }
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
        mViewModel = new UserViewModel();
        mOauthViewModel = new OauthViewModel();
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

        int userHeight = ViewUtil.getViewSize(mUserContainer)[1];
        ValueAnimator userAnimator = AnimHelper.createDropDown(mUserContainer, 0, userHeight);
        userAnimator.start();

        int countHeight = ViewUtil.getViewSize(mTotalPhotosContainer)[1];
        ValueAnimator countAnimator = AnimHelper.createDropDown(mUserCountContainer, 0, countHeight);
        countAnimator.start();
    }

    private void hideUserProfile() {
        if (mUserContainer.getVisibility() == View.GONE) {
            return;
        }

        int userHeight = ViewUtil.getViewSize(mUserContainer)[1];
        ValueAnimator userAnimator = AnimHelper.createDropDown(mUserContainer, userHeight, 0);
        userAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mUserContainer.setVisibility(View.GONE);
            }
        });
        userAnimator.start();

        int countHeight = ViewUtil.getViewSize(mTotalPhotosContainer)[1];
        ValueAnimator countAnimator = AnimHelper.createDropDown(mUserCountContainer, countHeight, 0);
        countAnimator
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
        mViewModel.getMyProfile().observe(this, new ResourceObserver<Resource<User>, User>(this) {
            @Override
            protected void onSuccess(User user) {
                mViewModel.setUser(user);

                mUsername.setText(user.username);
                mName.setText(user.name);
                mTotalLikes.setText(user.total_likes + "");
                mTotalPhotos.setText(user.total_photos + "");
                mTotalCollections.setText(user.total_collections + "");
                ImgUtil.loadImg(SettingActivity.this, mAvatar, user.profile_image.medium);
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

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstant.getLoginUrl())));
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.user_container, R.id.total_photos_container, R.id.total_collections_container, R.id.total_likes_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_container:
                NavHelper.toProfile(this, mViewModel.getUserData());
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
