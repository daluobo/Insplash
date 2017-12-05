package daluobo.insplash.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.PhotosAdapter;
import daluobo.insplash.base.view.SwipeListFragment;
import daluobo.insplash.helper.AnimHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Photo;
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

        mViewModel = new PhotoViewModel();
        mAdapter = new PhotosAdapter(getContext());
    }

    @Override
    public void initView() {
        View titleView = mInflater.inflate(R.layout.header_photos, null);
        mPhotoType = titleView.findViewById(R.id.photo_type);
        mPhotoType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View contentView = mInflater.inflate(R.layout.dialog_photo_type, null, false);
                final TextView all = contentView.findViewById(R.id.all);
                final TextView curated = contentView.findViewById(R.id.curated);

                int allWidth = ViewHelper.getViewSize(all)[0];
                int curatedWidth = ViewHelper.getViewSize(curated)[0];

                final PopupWindow window = new PopupWindow(contentView,
                        allWidth > curatedWidth ? allWidth : curatedWidth,
                        mPhotoType.getHeight() * 2,
                        true);
                window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                window.setOutsideTouchable(true);
                window.setTouchable(true);
                window.setElevation(10);
                window.setAnimationStyle(R.style.showPopupAnimation);
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
        });

        mOrderBy = titleView.findViewById(R.id.order_by);
        mOrderBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View contentView = mInflater.inflate(R.layout.dialog_order_by, null, false);

                final PopupWindow window = new PopupWindow(contentView,
                        ViewHelper.getScreenSize(getContext())[0],
                        ViewHelper.getScreenSize(getContext())[1],
                        true);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setOutsideTouchable(true);
                window.setTouchable(true);

                window.showAsDropDown(mOrderBy, 0, -mPhotoType.getHeight());


            }
        });
        mHeaderContainer.addView(titleView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        super.initListView();
    }

}
