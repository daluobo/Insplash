package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

public class CompatPhotoViewHolder extends PhotoViewHolder {

    @BindView(R.id.container)
    RelativeLayout mContainer;
    @BindView(R.id.avatar)
    ImageView mAvatar;

    int mColumn = 1;

    public CompatPhotoViewHolder(View itemView, Context context, int column, OnActionClickListener onActionClickListener) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);

        mColumn = column;
        mOnActionClickListener = onActionClickListener;

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
        } else if (mColumn == 2) {
            mAvatar.setVisibility(View.VISIBLE);
            ImgUtil.loadImg(mContext, mAvatar, photo.user.profile_image.small);

            int width = ViewUtil.getScreenSize(mContext)[0] - (mColumn + 1) * DimensionUtil.dpToPx(6);

            lp.width = width / mColumn;
            lp.height = lp.width * 3 / 2;
        } else {
            mLikeBtn.setVisibility(View.GONE);
            mLikes.setVisibility(View.GONE);

            int width = ViewUtil.getScreenSize(mContext)[0] - (mColumn + 1) * DimensionUtil.dpToPx(6);

            lp.width = width / mColumn;
            lp.height = lp.width;
        }

        mPhotoView.setLayoutParams(lp);
        ImgUtil.loadImg(mContext, mPhotoView, ViewUtil.createColorDrawable(photo.color), photo.urls.small);
    }

}
