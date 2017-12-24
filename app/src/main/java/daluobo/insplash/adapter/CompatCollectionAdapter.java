package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/24.
 */

public class CompatCollectionAdapter extends FooterAdapter<Collection> {
    protected Context mContext;

    public CompatCollectionAdapter(Context context, List<Collection> data) {
        this.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Collection item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mCollection = item;

        holder.mTitle.setText(item.title);
        holder.mTotalPhotos.setText(item.total_photos + "  Photos");
        ImgUtil.loadImgCC(mContext, holder.mAvatar, item.user.profile_image.small);
        if (item.cover_photo == null) {
            return;
        }

        int screenWidth = ViewUtil.getScreenSize(mContext)[0];
        ColorDrawable bg = new ColorDrawable(Color.parseColor(item.cover_photo.color));
        ViewGroup.LayoutParams containerLp = holder.mPhotosContainer.getLayoutParams();

        holder.mPreview1.setVisibility(View.VISIBLE);
        holder.mPreview2.setVisibility(View.VISIBLE);

        if (item.preview_photos.size() >= 3) {
            containerLp.height = screenWidth / 3;
            holder.mPhotosContainer.setLayoutParams(containerLp);

            ImgUtil.loadImgCC(mContext, holder.mPreview0, bg, item.preview_photos.get(0).urls.thumb);
            ImgUtil.loadImgCC(mContext, holder.mPreview1, bg, item.preview_photos.get(1).urls.thumb);
            ImgUtil.loadImgCC(mContext, holder.mPreview2, bg, item.preview_photos.get(2).urls.thumb);
        } else if (item.preview_photos.size() == 2) {
            holder.mPreview2.setVisibility(View.GONE);

            containerLp.height = screenWidth / 2;
            holder.mPhotosContainer.setLayoutParams(containerLp);

            ImgUtil.loadImgCC(mContext, holder.mPreview0, bg, item.preview_photos.get(0).urls.small);
            ImgUtil.loadImgCC(mContext, holder.mPreview1, bg, item.preview_photos.get(1).urls.small);
        } else {
            holder.mPreview1.setVisibility(View.GONE);
            holder.mPreview2.setVisibility(View.GONE);

            containerLp.height = screenWidth * 2 / 3;
            holder.mPhotosContainer.setLayoutParams(containerLp);

            if (item.cover_photo != null) {
                ImgUtil.loadImgCC(mContext, holder.mPreview0, bg, item.cover_photo.urls.small);
            } else {
                ImgUtil.loadImg(mContext, holder.mPreview0, holder.mIcNeutral);
            }
        }

    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_collection_compat));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.preview_0)
        ImageView mPreview0;
        @BindView(R.id.preview_1)
        ImageView mPreview1;
        @BindView(R.id.preview_2)
        ImageView mPreview2;
        @BindView(R.id.photos_container)
        LinearLayout mPhotosContainer;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.avatar)
        ImageView mAvatar;
        @BindView(R.id.container)
        RelativeLayout mContainer;
        @BindView(R.id.info_container)
        LinearLayout mInfoContainer;
        @BindView(R.id.total_photos)
        TextView mTotalPhotos;

        Collection mCollection;

        @BindDrawable(R.drawable.ic_neutral)
        Drawable mIcNeutral;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mPhotosContainer.setOnClickListener(this);
            mAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHelper.toUser(mContext, mCollection.user, mAvatar);
                }
            });
        }

        @Override
        public void onClick(View view) {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            int x = location[0];
            int y = location[1];

            NavHelper.toCollection(mContext, mCollection, x + view.getWidth() / 2, y + mPreview0.getHeight() / 2);
        }
    }
}
