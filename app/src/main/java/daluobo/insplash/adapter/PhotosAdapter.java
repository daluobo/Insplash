package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.DimensionUtil;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotosAdapter extends FooterAdapter<Photo> {

    private Context mContext;

    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        mOnLikeClickListener = onLikeClickListener;
    }

    private OnLikeClickListener mOnLikeClickListener;

    public PhotosAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Photo item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

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

        holder.mUsername.setText(item.user.name);

        ViewGroup.LayoutParams lp = holder.mPhotoView.getLayoutParams();
        lp.width = ViewHelper.getScreenSize(mContext)[0] - DimensionUtil.dip2px(mContext, 8);
        lp.height = lp.width * item.height / item.width;
        holder.mPhotoView.setLayoutParams(lp);

        ImgHelper.loadImg(mContext, holder.mAvatar, item.user.profile_image.small);
        ImgHelper.loadImg(mContext, holder.mPhotoView, new ColorDrawable(Color.parseColor(item.color)), item.urls.small);
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_photo));
    }

    public interface OnLikeClickListener {
        void OnLikeClick(ImageView imageView, Photo photo);
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

        Photo mPhoto;
        @BindDrawable(R.drawable.ic_favorite_border)
        Drawable mIcFavoriteBorder;
        @BindDrawable(R.drawable.ic_favorite)
        Drawable mIcFavorite;

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
        }

        @Override
        public void onClick(View v) {
            NavHelper.toPhoto(mContext, mPhoto, mPhotoView);
        }
    }
}
