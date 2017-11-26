package daluobo.insplash.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.DateUtil;
import daluobo.insplash.viewmodel.PhotoViewModel;

public class PhotoActivity extends BaseActivity {
    public static final String ARG_PHOTO = "photo";
    protected Photo mPhoto;
    protected PhotoViewModel mViewModel;

    @BindDrawable(R.drawable.ic_arrow_up)
    Drawable mIcArrowUp;
    @BindDrawable(R.drawable.ic_arrow_down)
    Drawable mIcArrowDown;

    @BindView(R.id.photo_view)
    ImageView mPhotoView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.exif_model)
    TextView mExifModel;
    @BindView(R.id.size)
    TextView mSize;
    @BindView(R.id.user_avatar)
    ImageView mUserAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.exposure_time)
    TextView mExposureTime;
    @BindView(R.id.aperture)
    TextView mAperture;
    @BindView(R.id.focal_length)
    TextView mFocalLength;
    @BindView(R.id.iso)
    TextView mIso;
    @BindView(R.id.exif_container)
    LinearLayout mExifContainer;
    @BindView(R.id.like_btn)
    ImageView mLikeBtn;
    @BindView(R.id.like_container)
    LinearLayout mLikeContainer;
    @BindView(R.id.mark_container)
    LinearLayout mMarkContainer;
    @BindView(R.id.download_container)
    LinearLayout mDownloadContainer;
    @BindView(R.id.exif_container_hint)
    ImageView mExifContainerHint;
    @BindView(R.id.show_exif_btn)
    RelativeLayout mShowExifBtn;
    @BindView(R.id.label_exposure_time)
    TextView mLabelExposureTime;
    @BindView(R.id.label_aperture)
    TextView mLabelAperture;
    @BindView(R.id.label_focal_length)
    TextView mLabelFocalLength;
    @BindView(R.id.label_iso)
    TextView mLabelIso;
    @BindView(R.id.created_at)
    TextView mCreatedAt;
    @BindView(R.id.like_count)
    TextView mLikeCount;
    @BindView(R.id.views_count)
    TextView mViewsCount;
    @BindView(R.id.download_count)
    TextView mDownloadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    public void initData() {
        mPhoto = getIntent().getParcelableExtra(ARG_PHOTO);

        mViewModel = new PhotoViewModel();
    }

    @Override
    public void initView() {
        ViewGroup.LayoutParams lp = mPhotoView.getLayoutParams();
        lp.width = ViewHelper.getScreenSize(this)[0];
        lp.height = lp.width * mPhoto.height / mPhoto.width;
        mPhotoView.setLayoutParams(lp);

        ColorDrawable bg = new ColorDrawable(Color.parseColor(mPhoto.color));
        //mAppBar.setBackground(bg);

        ImgHelper.loadImg(this, mPhotoView, bg, mPhoto.urls.small);

        mLikeCount.setText(mPhoto.likes + "");

        mCreatedAt.setText(DateUtil.GmtFormat(mPhoto.created_at));
        mSize.setText(mPhoto.width + " x " + mPhoto.height);

        ImgHelper.loadImg(PhotoActivity.this, mUserAvatar, mPhoto.user.profile_image.medium);
        mUsername.setText(mPhoto.user.username);

        mViewModel.getPhoto(mPhoto.id).observe(this, new ResourceObserver<Resource<Photo>, Photo>(this) {
            @Override
            protected void onSuccess(Photo photo) {
                if (photo.description != null) {
                    mDescription.setText(photo.description);
                } else {
                    mDescription.setVisibility(View.GONE);
                }
                if (photo.location != null) {
                    mLocation.setText(photo.location.title);
                }
                if (photo.exif != null) {
                    mExifModel.setText(photo.exif.model);

                    mExposureTime.setText(photo.exif.exposure_time);
                    mAperture.setText(photo.exif.aperture);
                    mFocalLength.setText(photo.exif.focal_length);
                    mIso.setText(photo.exif.iso + "");
                }

                mViewsCount.setText(photo.views + "");
                mDownloadCount.setText(photo.downloads + "");
            }
        });
    }

    @OnClick({R.id.like_container, R.id.mark_container, R.id.download_container, R.id.show_exif_btn,R.id.collect_container,R.id.user_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.like_container:
                break;
            case R.id.mark_container:
                break;
            case R.id.download_container:
                break;
            case R.id.show_exif_btn:
                if (mExifContainer.getVisibility() == View.VISIBLE) {
                    mExifContainer.setVisibility(View.GONE);
                    mExifContainerHint.setImageDrawable(mIcArrowDown);
                } else {
                    mExifContainer.setVisibility(View.VISIBLE);
                    mExifContainerHint.setImageDrawable(mIcArrowUp);
                }
                break;
            case R.id.user_container:
                break;
            case R.id.collect_container:
                break;
        }
    }
}
