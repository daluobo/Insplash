package daluobo.insplash.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.viewmodel.PhotoViewModel;

public class AboutActivity extends BaseActivity {
    private PhotoViewModel mViewModel;
    private final Map<String, Object> mParam = new HashMap<>();

    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.root_container)
    CoordinatorLayout mRootContainer;

    @BindDrawable(R.drawable.ic_neutral)
    Drawable mIcNeutral;

    Drawable mImgBg;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({Orientation.LANDSCAPE, Orientation.PORTRAIT, Orientation.SQUARISH})
    public @interface Orientation {
        String LANDSCAPE = "landscape";
        String PORTRAIT = "portrait";
        String SQUARISH = "squarish";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    public void initData() {
        mViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        //mParam.put("query", "tech");
        mParam.put("featured", true);
        mParam.put("orientation", Orientation.LANDSCAPE);
    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBackdrop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBackdrop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mParam.put("w", mBackdrop.getWidth() * 2 / 3);

                mViewModel.getRandom(mParam).observe(AboutActivity.this, new ResourceObserver<Resource<Photo>, Photo>(AboutActivity.this) {
                    @Override
                    protected void onSuccess(Photo photo) {
                        ImgUtil.loadImg(AboutActivity.this, mBackdrop, photo.urls.custom);
                        mImgBg = new ColorDrawable(Color.parseColor(photo.color));
                    }
                });
            }
        });

        mBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    private void refresh() {
        mViewModel.getRandom(mParam).observe(this, new ResourceObserver<Resource<Photo>, Photo>(this) {
            @Override
            protected void onSuccess(Photo photo) {
                ImgUtil.loadImg(AboutActivity.this, mBackdrop, mImgBg, photo.urls.custom);
                mImgBg = new ColorDrawable(Color.parseColor(photo.color));
            }
        });
    }
}
