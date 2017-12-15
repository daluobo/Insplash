package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotosAdapter extends FooterAdapter<Photo> {
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean mIsShowUser = true;

    private PopupWindow mPopupWindow;
    private int[] mPopupWindowSize;
    private TextView mDownloadBtn;
    private TextView mCollectBtn;
    private TextView mDeleteBtn;
    private int PopupWindowPhotoPosition = -1;

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        mOnMenuClickListener = onMenuClickListener;
    }

    private OnMenuClickListener mOnMenuClickListener;

    public PhotosAdapter(Context context, List<Photo> data) {
        this.mContext = context;
        super.mData = data;

        mInflater = LayoutInflater.from(mContext);
    }

    public PhotosAdapter(Context context, List<Photo> data, boolean isShowUser) {
        this(context, data);
        this.mIsShowUser = isShowUser;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Photo item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mPosition = position;

        if (item == null) {
            return;
        }
        holder.mPhoto = item;

        if (item.description != null) {
            holder.mDescription.setText(item.description);
            holder.mDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mDescription.setVisibility(View.GONE);
        }

        if (item.liked_by_user) {
            holder.mLikeBtn.setImageDrawable(holder.mIcFavorite);
        } else {
            holder.mLikeBtn.setImageDrawable(holder.mIcFavoriteBorder);
        }
        holder.mLikes.setText(item.likes + "");

        if (mIsShowUser) {
            holder.mUsername.setText(item.user.name);
            ImgUtil.loadImg(mContext, holder.mAvatar, item.user.profile_image.small);
            holder.mUsername.setVisibility(View.VISIBLE);
            holder.mAvatar.setVisibility(View.VISIBLE);
        } else {
            holder.mUsername.setVisibility(View.GONE);
            holder.mAvatar.setVisibility(View.GONE);
        }

        ViewGroup.LayoutParams lp = holder.mPhotoView.getLayoutParams();
        lp.width = ViewUtil.getScreenSize(mContext)[0] - DimensionUtil.dip2px(mContext, 8);
        lp.height = lp.width * item.height / item.width;
        holder.mPhotoView.setLayoutParams(lp);

        ImgUtil.loadImg(mContext, holder.mPhotoView, new ColorDrawable(Color.parseColor(item.color)), item.urls.small);
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_photo));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.photo_view)
        ImageView mPhotoView;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.avatar)
        ImageView mAvatar;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.like_btn)
        ImageView mLikeBtn;
        @BindView(R.id.likes)
        TextView mLikes;
        @BindView(R.id.more)
        ImageView mMore;
        @BindView(R.id.container)
        CardView mContainer;

        int mPosition;
        Photo mPhoto;

        @BindDrawable(R.drawable.ic_favorite_border)
        Drawable mIcFavoriteBorder;
        @BindDrawable(R.drawable.ic_favorite)
        Drawable mIcFavorite;
        @BindDrawable(R.drawable.ic_loop)
        Drawable mIcLoop;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mPhotoView.setOnClickListener(this);

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
                    showMoreDialog(v, mPosition);
                }
            });

            mLikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLikeBtn.setImageDrawable(mIcLoop);

                    RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                            0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(800);
                    animation.setRepeatCount(Animation.INFINITE);
                    animation.setRepeatMode(Animation.RESTART);
                    animation.setInterpolator(new LinearInterpolator());
                    mLikeBtn.startAnimation(animation);
                }
            });
        }

        @Override
        public void onClick(View v) {
            NavHelper.toPhoto(mContext, mPhoto, mPhotoView);
        }
    }

    private void showMoreDialog(View view, final int position) {
        if (mPopupWindow == null) {
            initPopupWindow();
        }
        PopupWindowPhotoPosition = position;

        int[] location = new int[2];
        view.getLocationInWindow(location);

        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - mPopupWindowSize[0], location[1] - mPopupWindowSize[1]);
    }

    private void initPopupWindow() {
        final View contentView = mInflater.inflate(R.layout.menu_view_photo, null, false);
        mPopupWindowSize = ViewUtil.getViewSize(contentView);

        mDownloadBtn = contentView.findViewById(R.id.download_tv);
        mCollectBtn = contentView.findViewById(R.id.collect_tv);
        mDeleteBtn = contentView.findViewById(R.id.delete_tv);

        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownloadClick();
            }
        });

        mCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCollectClick();
            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick();
            }
        });


        mPopupWindow = new PopupWindow(contentView,
                mPopupWindowSize[0],
                mPopupWindowSize[1],
                true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setElevation(10);
        mPopupWindow.setAnimationStyle(R.style.ScaleAnimation);
    }


    public void onDownloadClick() {

    }

    public void onCollectClick() {
        mPopupWindow.dismiss();
        mOnMenuClickListener.onCollectClick();
    }

    public void onDeleteClick() {

    }

    public interface OnMenuClickListener {
        void onCollectClick();

    }


}
