package daluobo.insplash.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IntDef;
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
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.ImgHelper;
import daluobo.insplash.helper.ViewHelper;
import daluobo.insplash.model.Collection;
import daluobo.insplash.util.DimensionUtil;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsAdapter extends FooterAdapter<Collection> {


    @IntDef({CollectionViewType.COLLECTION_NORMAL, CollectionViewType.COLLECTION_PREVIEW})
    private @interface CollectionViewType {
        int COLLECTION_NORMAL = 10;
        int COLLECTION_PREVIEW = 11;
    }

    private Context mContext;

    public CollectionsAdapter(Context context) {
        this.mContext = context;
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
            if (item.cover_photo != null) {
                CoverViewHolder cvh = (CoverViewHolder) viewHolder;

                ViewGroup.LayoutParams lp = cvh.mCoverPhoto.getLayoutParams();
                lp.width = ViewHelper.getScreenSize(mContext)[0];
                lp.height = lp.width * item.cover_photo.height / item.cover_photo.width;
                cvh.mCoverPhoto.setLayoutParams(lp);

                ImgHelper.loadImg(mContext, cvh.mCoverPhoto, new ColorDrawable(Color.parseColor(item.cover_photo.color)), item.cover_photo.urls.small);
            }
        } else if (viewHolder instanceof PreViewHolder) {
            PreViewHolder pvh = (PreViewHolder) viewHolder;

            ViewGroup.LayoutParams containerLp = pvh.mPreviewContainer.getLayoutParams();

            int containerWidth = ViewHelper.getScreenSize(mContext)[0] - DimensionUtil.dip2px(mContext, 8);
            int width = containerWidth / 3;

            containerLp.height = width;
            pvh.mPreviewContainer.setLayoutParams(containerLp);

            ImgHelper.loadImgCC(mContext, pvh.mPreview0, new ColorDrawable(Color.parseColor(item.cover_photo.color)), item.preview_photos.get(0).urls.thumb);
            ImgHelper.loadImgCC(mContext, pvh.mPreview1, new ColorDrawable(Color.parseColor(item.cover_photo.color)), item.preview_photos.get(1).urls.thumb);
            ImgHelper.loadImgCC(mContext, pvh.mPreview2, new ColorDrawable(Color.parseColor(item.cover_photo.color)), item.preview_photos.get(2).urls.thumb);
        }


        holder.mCollection = item;
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
        return new CoverViewHolder(inflateItemView(parent, R.layout.item_collection_cover));
    }

    private RecyclerView.ViewHolder onCreatePreViewHolder(ViewGroup parent, int viewType) {
        return new PreViewHolder(inflateItemView(parent, R.layout.item_collection_preview));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

    public class CoverViewHolder extends ViewHolder {
        @BindView(R.id.cover_photo)
        ImageView mCoverPhoto;

        Collection mCollection;

        public CoverViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContainer.setOnClickListener(this);
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

        }

    }
}
