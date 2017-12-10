package daluobo.insplash.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.DateUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;
import daluobo.insplash.viewmodel.PhotoViewModel;

public class PhotoActivity extends BaseActivity {
    public static final String ARG_PHOTO = "photo";
    protected Photo mPhoto;
    protected PhotoViewModel mViewModel;
    protected ColorDrawable mPhotoColor;
    protected int mPhotoColorId;

    @BindDrawable(R.drawable.ic_favorite_border)
    Drawable mIcFavoriteBorder;
    @BindDrawable(R.drawable.ic_favorite)
    Drawable mIcFavorite;
    @BindDrawable(R.drawable.ic_visibility)
    Drawable mIcVisibility;
    @BindDrawable(R.drawable.ic_file_download)
    Drawable mIcDownload;
    @BindDrawable(R.drawable.ic_place)
    Drawable mIcPlace;
    @BindDrawable(R.drawable.ic_time)
    Drawable mIcTime;
    @BindDrawable(R.drawable.ic_camera)
    Drawable mIcCamera;
    @BindDrawable(R.drawable.ic_arrow_down)
    Drawable mIcArrowDown;
    @BindDrawable(R.drawable.ic_straighten)
    Drawable mIcStraighten;
    @BindDrawable(R.drawable.ic_palette)
    Drawable mIcPalette;
    @BindDrawable(R.drawable.ic_person_outline)
    Drawable mIcPersonOutline;
    @BindDrawable(R.drawable.ic_mark_border)
    Drawable mIcMarkBorder;
    @BindDrawable(R.drawable.ic_mark)
    Drawable mIcMark;
    @BindView(R.id.photo_view)
    ImageView mPhotoView;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.like_count)
    TextView mLikeCount;
    @BindView(R.id.views_count)
    TextView mViewsCount;
    @BindView(R.id.download_count)
    TextView mDownloadCount;
    @BindView(R.id.location)
    TextView mLocation;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.exif_model)
    TextView mExifModel;
    @BindView(R.id.exif_container_hint)
    ImageView mExifContainerHint;
    @BindView(R.id.show_exif_btn)
    RelativeLayout mShowExifBtn;
    @BindView(R.id.label_exposure_time)
    TextView mLabelExposureTime;
    @BindView(R.id.exposure_time)
    TextView mExposureTime;
    @BindView(R.id.label_aperture)
    TextView mLabelAperture;
    @BindView(R.id.aperture)
    TextView mAperture;
    @BindView(R.id.label_focal_length)
    TextView mLabelFocalLength;
    @BindView(R.id.focal_length)
    TextView mFocalLength;
    @BindView(R.id.label_iso)
    TextView mLabelIso;
    @BindView(R.id.iso)
    TextView mIso;
    @BindView(R.id.exif_container)
    LinearLayout mExifContainer;
    @BindView(R.id.size)
    TextView mSize;
    @BindView(R.id.color)
    TextView mColor;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.user_container)
    LinearLayout mUserContainer;
    @BindView(R.id.collect)
    TextView mCollect;


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

        mViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
    }

    @Override
    public void initView() {
        ViewGroup.LayoutParams lp = mPhotoView.getLayoutParams();
        lp.width = ViewUtil.getScreenSize(this)[0];
        lp.height = lp.width * mPhoto.height / mPhoto.width;
        mPhotoView.setLayoutParams(lp);

        mPhotoColorId = Color.parseColor(mPhoto.color);
        mPhotoColor = new ColorDrawable(mPhotoColorId);

        ImgUtil.loadImg(this, mPhotoView, mPhotoColor, mPhoto.urls.small);

        if (mPhoto.description != null) {
            mDescription.setText(mPhoto.description);
            mDescription.setVisibility(View.VISIBLE);

            mDescription.getViewTreeObserver().
                    addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            //避免重复监听
                            mDescription.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            int height = mDescription.getMeasuredHeight();

                            ValueAnimator animator = AnimHelper.createDropDown(mDescription, 0, height);
                            animator.setDuration(800).start();
                        }
                    });

        } else {
            mDescription.setVisibility(View.GONE);
        }

        mLikeCount.setText(mPhoto.likes + "");

        String time;
        if (mPhoto.created_at != null) {
            time = DateUtil.GmtFormat(mPhoto.created_at);
        } else {
            time = DateUtil.GmtFormat(mPhoto.updated_at);
        }
        mTime.setText(time);
        mSize.setText(mPhoto.width + " x " + mPhoto.height);

        ImgUtil.loadImg(PhotoActivity.this, mAvatar, mPhoto.user.profile_image.medium);
        mUsername.setText(mPhoto.user.username);
        mColor.setText(mPhoto.color);

        initIcon();

        mViewModel.getPhoto(mPhoto.id).observe(this, new ResourceObserver<Resource<Photo>, Photo>(this) {
            @Override
            protected void onSuccess(Photo photo) {
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

    private void initIcon() {
        ViewUtil.setDrawableTop(mLikeCount, ViewUtil.tintDrawable(mIcFavoriteBorder, mPhotoColorId));
        ViewUtil.setDrawableTop(mViewsCount, ViewUtil.tintDrawable(mIcVisibility, mPhotoColorId));
        ViewUtil.setDrawableTop(mDownloadCount, ViewUtil.tintDrawable(mIcDownload, mPhotoColorId));

        ViewUtil.setDrawableStart(mLocation, ViewUtil.tintDrawable(mIcPlace, mPhotoColorId));
        ViewUtil.setDrawableStart(mTime, ViewUtil.tintDrawable(mIcTime, mPhotoColorId));
        ViewUtil.setDrawableStart(mExifModel, ViewUtil.tintDrawable(mIcCamera, mPhotoColorId));

        mExifContainerHint.setImageDrawable(ViewUtil.tintDrawable(mIcArrowDown, mPhotoColorId));

        ViewUtil.setDrawableStart(mSize, ViewUtil.tintDrawable(mIcStraighten, mPhotoColorId));
        ViewUtil.setDrawableStart(mColor, ViewUtil.tintDrawable(mIcPalette, mPhotoColorId));

        ViewUtil.setDrawableEnd(mUsername, ViewUtil.tintDrawable(mIcPersonOutline, mPhotoColorId));
        ViewUtil.setDrawableEnd(mCollect, ViewUtil.tintDrawable(mIcMarkBorder, mPhotoColorId));
    }

    @OnClick({R.id.like_count, R.id.download_count, R.id.show_exif_btn, R.id.user_container, R.id.collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.like_count:
                break;
            case R.id.download_count:
                break;
            case R.id.show_exif_btn:
                int height = ViewUtil.getViewSize(mExifContainer)[1];

                if (mExifContainer.getVisibility() == View.VISIBLE) {
                    mExifContainerHint.animate().rotation(0);

                    ValueAnimator animator = AnimHelper.createDropDown(mExifContainer, height, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mExifContainer.setVisibility(View.GONE);
                        }
                    });
                    animator.start();
                } else {
                    mExifContainer.setVisibility(View.VISIBLE);
                    mExifContainerHint.animate().rotation(180);

                    ValueAnimator animator = AnimHelper.createDropDown(mExifContainer, 0, height);
                    animator.start();
                }

                break;
            case R.id.user_container:
                NavHelper.toUser(this, mPhoto.user, mAvatar);

                break;
            case R.id.collect:

                break;
        }
    }
}
