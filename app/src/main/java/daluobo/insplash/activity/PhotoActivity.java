package daluobo.insplash.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Photo;

public class PhotoActivity extends BaseActivity {
    public static final String ARG_PHOTO = "photo";
    protected Photo mPhoto;

    @BindView(R.id.photo_view)
    ImageView mPhotoView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

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
    }

    @Override
    public void initView() {
        ViewGroup.LayoutParams lp = mPhotoView.getLayoutParams();
        lp.width = ViewHelper.getScreenSize(this)[0];
        lp.height = lp.width * mPhoto.height / mPhoto.width;
        mPhotoView.setLayoutParams(lp);

        ColorDrawable bg = new ColorDrawable(Color.parseColor(mPhoto.color));

        ImgHelper.loadImg(this, mPhotoView, bg, mPhoto.urls.small);
    }
}
