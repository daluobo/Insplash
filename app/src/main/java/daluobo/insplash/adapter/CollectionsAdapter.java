package daluobo.insplash.adapter;

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
import daluobo.insplash.R;
import daluobo.insplash.activity.CollectionActivity;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Collection;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsAdapter extends FooterAdapter<Collection> {

    private Context mContext;

    public CollectionsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Collection item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mCollection = item;

        if (item.cover_photo != null) {
            ViewGroup.LayoutParams lp = holder.mCoverPhoto.getLayoutParams();
            lp.width = ViewHelper.getScreenSize(mContext)[0];
            lp.height = lp.width * item.cover_photo.height / item.cover_photo.width;
            holder.mCoverPhoto.setLayoutParams(lp);

            ImgHelper.loadImg(mContext, holder.mCoverPhoto, new ColorDrawable(Color.parseColor(item.cover_photo.color)), item.cover_photo.urls.small);
        }

        holder.mTitle.setText(item.title);

        if (item.description != null) {
            holder.mDescription.setText(item.description);
            holder.mDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mDescription.setVisibility(View.GONE);
        }

        ImgHelper.loadImg(mContext, holder.mProfileImage, item.user.profile_image.small);
        holder.mUsername.setText(item.user.name);
        holder.mTotalPhotos.setText(item.total_photos + "");
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_collection));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cover_photo)
        ImageView mCoverPhoto;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.profile_image)
        ImageView mProfileImage;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.total_photos)
        TextView mTotalPhotos;
        @BindView(R.id.container)
        CardView mContainer;

        @BindDrawable(R.drawable.ic_favorite_border)
        Drawable mIcFavoriteBorder;
        @BindDrawable(R.drawable.ic_favorite)
        Drawable mIcFavorite;

        Collection mCollection;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, CollectionActivity.class);
            intent.putExtra(CollectionActivity.ARG_COLLECTION, mCollection);
            mContext.startActivity(intent);
        }
    }
}
