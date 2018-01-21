package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.listener.OnActionClickListener;
import daluobo.insplash.helper.AuthHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/25.
 */

public class PhotoCardViewHolder extends PhotoViewHolder{
    @BindView(R.id.container)
    CardView mContainer;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.more)
    ImageView mMore;

    private int mContainerWidth;
    private boolean mIsShowUser = true;
    private PopupWindow mPopupWindow;
    private int[] mPopupWindowSize;

    public PhotoCardViewHolder(View itemView, Context context, boolean isShowUser, OnActionClickListener onMenuItemClickListener) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);

        mIsShowUser = isShowUser;
        mOnActionClickListener = onMenuItemClickListener;

        mContainerWidth = ViewUtil.getScreenSize(mContext)[0] - DimensionUtil.dip2px(mContext, 16);

        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHelper.toUser(mContext, mPhoto.user, mAvatar);
            }
        });

        mUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHelper.toUser(mContext, mPhoto.user, mAvatar);
            }
        });

        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreDialog(v);
            }
        });

        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AuthHelper.isLogin()) {
                    mLikeBtn.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);

                    mOnActionClickListener.onLikeClick(mPhoto);
                } else {
                    ToastUtil.showShort(mContext, mMsgPleaseLogin);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        NavHelper.toPhoto(mContext, mPhoto, mPhotoView);
    }

    public void bindDataToView(Photo photo, int position) {
        mPosition = position;

        if (photo == null) {
            return;
        }
        mPhoto = photo;
        ColorDrawable bg = new ColorDrawable(Color.parseColor(photo.color));

        ViewGroup.LayoutParams lp = mPhotoView.getLayoutParams();
        lp.width = mContainerWidth;
        lp.height = lp.width * photo.height / photo.width;
        mPhotoView.setLayoutParams(lp);
        ImgUtil.loadImg(mContext, mPhotoView, bg, photo.urls.small);

        if (photo.description != null) {
            mDescription.setText(photo.description);
            mDescription.setVisibility(View.VISIBLE);
        } else {
            mDescription.setVisibility(View.GONE);
        }

        if (mIsShowUser) {
            mUsername.setText(photo.user.name);
            ImgUtil.loadImg(mContext, mAvatar, photo.user.profile_image.small);
            mUsername.setVisibility(View.VISIBLE);
            mAvatar.setVisibility(View.VISIBLE);
        } else {
            mUsername.setVisibility(View.GONE);
            mAvatar.setVisibility(View.GONE);
        }

        mLikeBtn.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        if (photo.liked_by_user) {
            mLikeBtn.setImageDrawable(mIcFavorite);
        } else {
            mLikeBtn.setImageDrawable(mIcFavoriteBorder);
        }
        mLikes.setCurrentText(photo.likes + "");
    }

    private void showMoreDialog(View view) {
        if (mPopupWindow == null) {
            initPopupWindow();
        }

        int[] location = new int[2];
        view.getLocationInWindow(location);

        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - mPopupWindowSize[0], location[1] - mPopupWindowSize[1]);
    }

    private void initPopupWindow() {
        final View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_menu_photo, null, false);
        mPopupWindowSize = ViewUtil.getViewSize(contentView);

        TextView downloadBtn = contentView.findViewById(R.id.download_tv);
        TextView collectBtn = contentView.findViewById(R.id.collect_tv);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownloadClick(mPhoto);
            }
        });

        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCollectClick(mPhoto);
            }
        });

        mPopupWindow = new PopupWindow(contentView,
                mPopupWindowSize[0],
                mPopupWindowSize[1],
                true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.ScaleAnimation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPopupWindow.setElevation(10);
        }
    }


    public void onDownloadClick(Photo photo) {
        mPopupWindow.dismiss();
        mOnActionClickListener.onDownloadClick(photo);
    }

    public void onCollectClick(Photo photo) {
        if (AuthHelper.isLogin()) {
            mPopupWindow.dismiss();
            mOnActionClickListener.onCollectClick(photo);
        } else {
            ToastUtil.showShort(mContext, mMsgPleaseLogin);
        }

    }

}
