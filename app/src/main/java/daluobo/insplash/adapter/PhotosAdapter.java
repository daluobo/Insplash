package daluobo.insplash.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
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
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.activity.MainActivity;
import daluobo.insplash.activity.PhotoActivity;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Photo;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotosAdapter extends FooterAdapter<Photo> {
    private Context mContext;

    public PhotosAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Photo item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.mPhoto = item;

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
        holder.mLikes.setText(item.likes + "");

        holder.mUsername.setText(item.user.name);

        ViewGroup.LayoutParams lp = holder.mPhotoView.getLayoutParams();
        lp.width = ViewHelper.getScreenSize(mContext)[0];
        lp.height = lp.width * item.height / item.width;
        holder.mPhotoView.setLayoutParams(lp);

        ImgHelper.loadImg(mContext, holder.mProfileImage, item.user.profile_image.small);
        ImgHelper.loadImg(mContext, holder.mPhotoView, new ColorDrawable(Color.parseColor(item.color)), item.urls.small);
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_photo));
    }

    @OnClick(R.id.container)
    public void onViewClicked() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.container)
        CardView mContainer;
        @BindView(R.id.photo_view)
        ImageView mPhotoView;
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

        Photo mPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, PhotoActivity.class);
            intent.putExtra(PhotoActivity.ARG_PHOTO, mPhoto);
            mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((MainActivity) mContext, mPhotoView, "transitionImg").toBundle());
        }
    }
}
