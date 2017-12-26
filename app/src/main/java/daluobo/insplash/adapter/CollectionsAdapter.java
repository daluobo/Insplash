package daluobo.insplash.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import daluobo.insplash.R;
import daluobo.insplash.adapter.vh.CompatCollectionViewHolder;
import daluobo.insplash.adapter.vh.CoverViewHolder;
import daluobo.insplash.adapter.vh.PreViewHolder;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsAdapter extends FooterAdapter<Collection> {
    protected Context mContext;
    protected boolean mIsShowUser = true;

    @IntDef({CollectionViewType.COLLECTION_COVER, CollectionViewType.COLLECTION_PREVIEW, CollectionViewType.COLLECTION_COMPAT})
    private @interface CollectionViewType {
        int COLLECTION_COVER = 10;
        int COLLECTION_PREVIEW = 11;
        int COLLECTION_COMPAT = 12;
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
        } else if (ConfigHelper.isCompatView()) {
            return CollectionViewType.COLLECTION_COMPAT;
        } else {
            if (mData.get(position).preview_photos.size() >= 3) {
                return CollectionViewType.COLLECTION_PREVIEW;
            }
            return CollectionViewType.COLLECTION_COVER;
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Collection item, int position) {
        if (viewHolder instanceof CompatCollectionViewHolder) {
            CompatCollectionViewHolder ccvh = (CompatCollectionViewHolder) viewHolder;
            ccvh.bindDataToView(item, position);
        } else if (viewHolder instanceof CoverViewHolder) {
            CoverViewHolder cvh = (CoverViewHolder) viewHolder;
            cvh.bindDataToView(item, position);
        } else if (viewHolder instanceof PreViewHolder) {
            PreViewHolder pvh = (PreViewHolder) viewHolder;
            pvh.bindDataToView(item, position);
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CollectionViewType.COLLECTION_COMPAT:
                return new CompatCollectionViewHolder(inflateItemView(parent, R.layout.item_collection_compat), mContext);
            case CollectionViewType.COLLECTION_COVER:
                return new CoverViewHolder(inflateItemView(parent, R.layout.item_collection_cover), mContext, mIsShowUser);
            case CollectionViewType.COLLECTION_PREVIEW:
                return new PreViewHolder(inflateItemView(parent, R.layout.item_collection_preview), mContext, mIsShowUser);
        }

        return null;
    }

}
