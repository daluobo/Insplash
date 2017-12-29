package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
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

public class CompatPhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.photo_view)
    ImageView mPhotoView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.like_btn)
    ImageView mLikeBtn;
    @BindView(R.id.likes)
    TextSwitcher mLikes;
    @BindView(R.id.container)
    RelativeLayout mContainer;
    @BindView(R.id.avatar)
    ImageView mAvatar;

    @BindDrawable(R.drawable.ic_favorite_border)
    Drawable mIcFavoriteBorder;
    @BindDrawable(R.drawable.ic_favorite)
    Drawable mIcFavorite;
    @BindColor(R.color.colorTitle)
    int mColorText;
    @BindString(R.string.msg_please_login)
    String mMsgPleaseLogin;

    TextView mLikeText;

    OnActionClickListener mOnActionClickListener;
    Context mContext;
    Photo mPhoto;
    int mPosition;
    int mColumn = 1;

    public CompatPhotoViewHolder(View itemView, Context context, int column, OnActionClickListener onActionClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mContext = context;
        mColumn = column;
        mOnActionClickListener = onActionClickListener;

        mLikes.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                mLikeText = new TextView(mContext);
                mLikeText.setLayoutParams(new TextSwitcher.LayoutParams(TextSwitcher.LayoutParams.MATCH_PARENT, TextSwitcher.LayoutParams.MATCH_PARENT));
                mLikeText.setGravity(Gravity.CENTER);
                mLikeText.setBackgroundColor(Color.TRANSPARENT);
                mLikeText.setTextColor(mColorText);
                return mLikeText;
            }
        });

        mPhotoView.setOnClickListener(this);

        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AuthHelper.isLogin()) {
                    mLikeBtn.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);

                    mOnActionClickListener.onLikeClick(mPhoto);
                } else {
                    ToastUtil.showShort(mContext, mMsgPleaseLogin);
                }
            }
        });

        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHelper.toUser(mContext, mPhoto.user, mAvatar);
            }
        });
    }

    @Override
    public void onClick(View view) {
        NavHelper.toPhoto(mContext, mPhoto, mPhotoView);
    }

    public void bindDataToView(Photo photo, int position) {
        mPhoto = photo;
        mPosition = position;
        mLikes.setCurrentText(photo.likes + "");

        ViewGroup.LayoutParams lp = mPhotoView.getLayoutParams();
        if (mColumn <= 1) {
            mAvatar.setVisibility(View.VISIBLE);
            ImgUtil.loadImg(mContext, mAvatar, photo.user.profile_image.small);

            lp.width = ViewUtil.getScreenSize(mContext)[0];
            lp.height = lp.width * photo.height / photo.width;
        } else if (mColumn == 3) {
            mLikeBtn.setVisibility(View.GONE);
            mLikes.setVisibility(View.GONE);

            int width = ViewUtil.getScreenSize(mContext)[0] - mColumn * DimensionUtil.dpToPx(4);

            lp.width = width / mColumn;
            lp.height = lp.width;
        } else {
            int width = ViewUtil.getScreenSize(mContext)[0] - mColumn * DimensionUtil.dpToPx(4);

            lp.width = width / mColumn;
            lp.height = lp.width * 3 / 2;
        }

        mPhotoView.setLayoutParams(lp);
        ImgUtil.loadImg(mContext, mPhotoView, ViewUtil.createColorDrawable(photo.color), photo.urls.small);
    }

}
