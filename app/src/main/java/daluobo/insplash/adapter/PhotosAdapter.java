package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Photo;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotosAdapter extends BaseRecyclerAdapter<Photo, PhotosAdapter.ViewHolder> {
    Context mContext;

    public PhotosAdapter(Context context, List<Photo> data) {
        this.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(ViewHolder holder, Photo item, int position) {
        if (item.description != null) {
            holder.mDescription.setText(item.description);
            holder.mDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mDescription.setVisibility(View.GONE);
        }

        if (item.liked_by_user) {
            holder.mLikeIcon.setImageDrawable(holder.mIcFavorite);
        } else {
            holder.mLikeIcon.setImageDrawable(holder.mIcFavoriteBorder);
        }
        holder.mLikes.setText(item.likes);

        holder.mUsername.setText(item.user.name);

        ViewGroup.LayoutParams lp = holder.mPhoto.getLayoutParams();
        lp.width =  ViewHelper.getScreenSize(mContext)[0];
        lp.height = lp.width * item.height /item.width;
        holder.mPhoto.setLayoutParams(lp);

        holder.mPhoto.setImageDrawable(new ColorDrawable(Color.parseColor(item.color)));

        ImgHelper.loadImg(mContext, holder.mProfileImage, item.user.profile_image.small);
        ImgHelper.loadImg(mContext, holder.mPhoto, item.urls.small);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_photo));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo)
        ImageView mPhoto;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.profile_image)
        ImageView mProfileImage;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.like_icon)
        ImageView mLikeIcon;
        @BindView(R.id.likes)
        TextView mLikes;

        @BindDrawable(R.drawable.ic_favorite_border)
        Drawable mIcFavoriteBorder;
        @BindDrawable(R.drawable.ic_favorite)
        Drawable mIcFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
