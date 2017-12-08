package daluobo.insplash.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.activity.CollectionActivity;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Collection;
import daluobo.insplash.util.DimensionUtil;

/**
 * Created by daluobo on 2017/12/7.
 */

public class UserCollectionsAdapter extends CollectionsAdapter {

    public UserCollectionsAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Collection item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (viewHolder instanceof UserCollectionsAdapter.CoverViewHolder) {
            if (item.cover_photo != null) {
                UserCollectionsAdapter.CoverViewHolder cvh = (UserCollectionsAdapter.CoverViewHolder) viewHolder;

                ViewGroup.LayoutParams lp = cvh.mCoverPhoto.getLayoutParams();
                lp.width = ViewHelper.getScreenSize(mContext)[0];
                lp.height = lp.width * item.cover_photo.height / item.cover_photo.width;
                cvh.mCoverPhoto.setLayoutParams(lp);

                ImgHelper.loadImg(mContext, cvh.mCoverPhoto, new ColorDrawable(Color.parseColor(item.cover_photo.color)), item.cover_photo.urls.small);
            }
        } else if (viewHolder instanceof UserCollectionsAdapter.PreViewHolder) {
            UserCollectionsAdapter.PreViewHolder pvh = (UserCollectionsAdapter.PreViewHolder) viewHolder;

            ViewGroup.LayoutParams containerLp = pvh.mPreviewContainer.getLayoutParams();

            int containerWidth = ViewHelper.getScreenSize(mContext)[0] - DimensionUtil.dip2px(mContext, 8);
            int width = containerWidth / 3;

            containerLp.height = width;
            pvh.mPreviewContainer.setLayoutParams(containerLp);

            ColorDrawable bg = new ColorDrawable(Color.parseColor(item.cover_photo.color));

            ImgHelper.loadImgCC(mContext, pvh.mPreview0, bg, item.preview_photos.get(0).urls.thumb);
            ImgHelper.loadImgCC(mContext, pvh.mPreview1, bg, item.preview_photos.get(1).urls.thumb);
            ImgHelper.loadImgCC(mContext, pvh.mPreview2, bg, item.preview_photos.get(2).urls.thumb);
        }


        holder.mCollection = item;
        holder.mTitle.setText(item.title);

        if (item.description != null) {
            holder.mDescription.setText(item.description);
            holder.mDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mDescription.setVisibility(View.GONE);
        }

        holder.mTotalPhotos.setText(item.total_photos + "");
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new UserCollectionsAdapter.CoverViewHolder(inflateItemView(parent, R.layout.item_user_collection_cover));
    }

    @Override
    protected RecyclerView.ViewHolder onCreatePreViewHolder(ViewGroup parent, int viewType) {
        return new UserCollectionsAdapter.PreViewHolder(inflateItemView(parent, R.layout.item_user_collection_priview));
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.total_photos)
        TextView mTotalPhotos;
        @BindView(R.id.container)
        CardView mContainer;

        Collection mCollection;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public class CoverViewHolder extends ViewHolder {
        @BindView(R.id.cover_photo)
        ImageView mCoverPhoto;

        public CoverViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mCoverPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int[] location = new int[2];
            v.getLocationInWindow(location);
            int x = location[0];
            int y = location[1];

            Intent intent = new Intent(mContext, CollectionActivity.class);
            intent.putExtra(CollectionActivity.ARG_COLLECTION, mCollection);

            intent.putExtra(CollectionActivity.ARG_REVEAL_X, x + v.getWidth() / 2);
            intent.putExtra(CollectionActivity.ARG_REVEAL_Y, y + mCoverPhoto.getHeight() / 2);

            mContext.startActivity(intent);
        }
    }

    public class PreViewHolder extends ViewHolder {
        @BindView(R.id.preview_container)
        LinearLayout mPreviewContainer;
        @BindView(R.id.preview_0)
        ImageView mPreview0;
        @BindView(R.id.preview_1)
        ImageView mPreview1;
        @BindView(R.id.preview_2)
        ImageView mPreview2;

        public PreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mPreviewContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int[] location = new int[2];
            v.getLocationInWindow(location);
            int x = location[0];
            int y = location[1];

            Intent intent = new Intent(mContext, CollectionActivity.class);
            intent.putExtra(CollectionActivity.ARG_COLLECTION, mCollection);

            intent.putExtra(CollectionActivity.ARG_REVEAL_X, x + v.getWidth() / 2);
            intent.putExtra(CollectionActivity.ARG_REVEAL_Y, y + mPreview0.getHeight() / 2);

            mContext.startActivity(intent);
        }
    }
}
