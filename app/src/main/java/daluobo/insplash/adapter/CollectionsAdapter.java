package daluobo.insplash.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.activity.CollectionActivity;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsAdapter extends FooterAdapter<Collection> {
    protected Context mContext;
    protected boolean mIsShowUser = true;

    @IntDef({CollectionViewType.COLLECTION_NORMAL, CollectionViewType.COLLECTION_PREVIEW})
    private @interface CollectionViewType {
        int COLLECTION_NORMAL = 10;
        int COLLECTION_PREVIEW = 11;
    }


    public CollectionsAdapter(Context context, List<Collection> data) {
        this.mContext = context;
        super.mData = data;
    }

    public CollectionsAdapter(Context context, List<Collection> data, boolean isShowUser) {
        this(context, data);
        this.mIsShowUser = isShowUser;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowFooter && position == getItemCount() - 1) {
            return ItemViewType.FOOTER_TYPE;
        } else {
            if (mData.get(position).preview_photos.size() >= 3) {
                return CollectionViewType.COLLECTION_PREVIEW;
            }
            return CollectionViewType.COLLECTION_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemViewType.FOOTER_TYPE:
                return onCreateFooterViewHolder(parent, viewType);
            case CollectionViewType.COLLECTION_NORMAL:
                return onCreateItemViewHolder(parent, viewType);
            case CollectionViewType.COLLECTION_PREVIEW:
                return onCreatePreViewHolder(parent, viewType);
            default:
                return onCreateItemViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Collection item = getItem(position);

        switch (getItemViewType(position)) {
            case CollectionViewType.COLLECTION_NORMAL:
                bindDataToItemView(viewHolder, item, position);
                break;
            case CollectionViewType.COLLECTION_PREVIEW:
                bindDataToItemView(viewHolder, item, position);
                break;
            case ItemViewType.FOOTER_TYPE:
                break;
        }

    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Collection item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (viewHolder instanceof CoverViewHolder) {
            CoverViewHolder cvh = (CoverViewHolder) viewHolder;
            ViewGroup.LayoutParams lp = cvh.mCoverPhoto.getLayoutParams();
            lp.width = cvh.mContainerWidth;
            lp.height = lp.width * 2 / 3;
            cvh.mCoverPhoto.setLayoutParams(lp);
            if (item.cover_photo != null) {
                ImgUtil.loadImg(mContext, cvh.mCoverPhoto, new ColorDrawable(Color.parseColor(item.cover_photo.color)), item.cover_photo.urls.small);
            } else {
                cvh.mCoverPhoto.setImageDrawable(cvh.mIcNeutral);
            }
        } else if (viewHolder instanceof PreViewHolder) {
            PreViewHolder pvh = (PreViewHolder) viewHolder;

            ViewGroup.LayoutParams containerLp = pvh.mPreviewContainer.getLayoutParams();

            int width = pvh.mContainerWidth / 3;

            containerLp.height = width;
            pvh.mPreviewContainer.setLayoutParams(containerLp);

            ColorDrawable bg = new ColorDrawable(Color.parseColor(item.cover_photo.color));

            ImgUtil.loadImgCC(mContext, pvh.mPreview0, bg, item.preview_photos.get(0).urls.thumb);
            ImgUtil.loadImgCC(mContext, pvh.mPreview1, bg, item.preview_photos.get(1).urls.thumb);
            ImgUtil.loadImgCC(mContext, pvh.mPreview2, bg, item.preview_photos.get(2).urls.thumb);
        }


        holder.mCollection = item;
        holder.mTitle.setText(item.title);

        if (item.description != null) {
            holder.mDescription.setText(item.description);
            holder.mDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mDescription.setVisibility(View.GONE);
        }

        if (mIsShowUser) {
            holder.mUsername.setText(item.user.name);
            ImgUtil.loadImg(mContext, holder.mAvatar, item.user.profile_image.small);

            holder.mUsername.setVisibility(View.VISIBLE);
            holder.mAvatar.setVisibility(View.VISIBLE);
        } else {
            holder.mUsername.setVisibility(View.INVISIBLE);
            if (item.privateX) {
                holder.mAvatar.setVisibility(View.VISIBLE);
                holder.mAvatar.setImageDrawable(holder.mIcLock);
            } else {
                holder.mAvatar.setVisibility(View.INVISIBLE);
            }
        }

        holder.mTotalPhotos.setText(item.total_photos + "");
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new CoverViewHolder(inflateItemView(parent, R.layout.item_collection_cover));
    }

    protected RecyclerView.ViewHolder onCreatePreViewHolder(ViewGroup parent, int viewType) {
        return new PreViewHolder(inflateItemView(parent, R.layout.item_collection_preview));
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.avatar)
        ImageView mAvatar;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.total_photos)
        TextView mTotalPhotos;
        @BindView(R.id.container)
        CardView mContainer;

        @BindDrawable(R.drawable.ic_lock_outline)
        Drawable mIcLock;

        @BindDrawable(R.drawable.ic_neutral)
        Drawable mIcNeutral;

        Collection mCollection;
        int mContainerWidth;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsShowUser) {
                        NavHelper.toUser(mContext, mCollection.user, mAvatar);
                    }
                }
            });

            mUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsShowUser) {
                        NavHelper.toUser(mContext, mCollection.user, mAvatar);
                    }
                }
            });

            mContainerWidth = ViewUtil.getScreenSize(mContext)[0] - DimensionUtil.dip2px(mContext, 8);
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
