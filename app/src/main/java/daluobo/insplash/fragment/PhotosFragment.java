package daluobo.insplash.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ViewUtil;
import daluobo.insplash.view.SetCollectDialog;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/11/9.
 */

public class PhotosFragment extends SwipeListFragment<List<Photo>> {


    protected LayoutInflater mInflater;
    private TextView mPhotoType;
    private TextView mOrderBy;

    @BindView(R.id.header_container)
    FrameLayout mHeaderContainer;
    @BindColor(R.color.colorBg)
    int mColorBg;

    public PhotosFragment() {
    }

    public static PhotosFragment newInstance() {
        PhotosFragment fragment = new PhotosFragment();
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        initData();
        initView();
        return view;
    }

    @Override
    public void initData() {
        mInflater = LayoutInflater.from(getContext());

        mViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        mAdapter = new PhotosAdapter(getContext(), mViewModel.getData());
        ((PhotosAdapter) mAdapter).setOnMenuClickListener(new PhotosAdapter.OnMenuClickListener() {
            @Override
            public void onCollectClick(Photo photo) {
                SetCollectDialog setCollectDialog = new SetCollectDialog();
                Bundle args = new Bundle();
                args.putParcelable(SetCollectDialog.ARG_PHOTO, photo);
                setCollectDialog.setArguments(args);
                setCollectDialog.show(getFragmentManager(), "SetCollectDialog");
            }
        });
    }

    @Override
    public void initView() {
        View titleView = mInflater.inflate(R.layout.header_photos, null);
        mPhotoType = titleView.findViewById(R.id.photo_type);
        mPhotoType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectType();
            }
        });

        mOrderBy = titleView.findViewById(R.id.order_by);
        mOrderBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectOrderBy();
            }
        });
        mHeaderContainer.addView(titleView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        initListView();
        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    private void showSelectType() {
        final View contentView = mInflater.inflate(R.layout.menu_photo_type, null, false);
        final TextView all = contentView.findViewById(R.id.all);
        final TextView curated = contentView.findViewById(R.id.curated);

        int allWidth = ViewUtil.getViewSize(all)[0];
        int curatedWidth = ViewUtil.getViewSize(curated)[0];

        final PopupWindow window = new PopupWindow(contentView,
                allWidth > curatedWidth ? allWidth : curatedWidth,
                mPhotoType.getHeight() * 2,
                true);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setElevation(10);
        window.setAnimationStyle(R.style.AlphaAnimation);
        window.showAsDropDown(mPhotoType, 0, -mPhotoType.getHeight());

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator valueAnimator = AnimHelper.createDropDown(contentView, mPhotoType.getHeight() * 2, mPhotoType.getHeight());
                valueAnimator.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mPhotoType.setText("Photos");
                        window.dismiss();
                    }

                });
                valueAnimator.start();

            }
        });
        curated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator valueAnimator = AnimHelper.createDropDown(all, mPhotoType.getHeight(), 0);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);

                        curated.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mPhotoType.setText("Curated");
                        window.dismiss();
                    }
                });

                ValueAnimator rootAnimator = AnimHelper.createDropDown(contentView, mPhotoType.getHeight() * 2, mPhotoType.getHeight());
                rootAnimator.setDuration(300);

                rootAnimator.start();
                valueAnimator.start();

            }
        });
    }

    private void showSelectOrderBy() {
        final View contentView = mInflater.inflate(R.layout.menu_order_by, null, false);
        final TextView latest = contentView.findViewById(R.id.latest);
        final TextView oldest = contentView.findViewById(R.id.oldest);
        final TextView popular = contentView.findViewById(R.id.popular);

        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        oldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        int latestWidth = ViewUtil.getViewSize(latest)[0];
        int oldestWidth = ViewUtil.getViewSize(oldest)[0];
        int popularWidth = ViewUtil.getViewSize(popular)[0];


        final PopupWindow window = new PopupWindow(contentView,
                popularWidth,
                mOrderBy.getHeight() * 3,
                true);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setElevation(10);
        window.setAnimationStyle(R.style.AlphaAnimation);

        window.showAsDropDown(mOrderBy, -DimensionUtil.dip2px(getContext(), 8), -mOrderBy.getHeight());

    }
}
