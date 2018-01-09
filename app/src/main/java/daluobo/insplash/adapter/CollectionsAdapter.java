package daluobo.insplash.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import daluobo.insplash.R;
import daluobo.insplash.adapter.vh.CollectionCardViewHolder;
import daluobo.insplash.adapter.vh.CollectionCompatViewHolder;
import daluobo.insplash.adapter.vh.CollectionViewHolder;
import daluobo.insplash.base.view.LoadableAdapter;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2017/11/27.
 */

public class CollectionsAdapter extends LoadableAdapter<Collection> {
    protected boolean mIsShowUser = true;

    @IntDef({CollectionViewType.COLLECTION_COMPAT, CollectionViewType.COLLECTION_CARD})
    private @interface CollectionViewType {
        int COLLECTION_COMPAT = 10;
        int COLLECTION_CARD = 11;
    }

    public CollectionsAdapter(Context context, List<Collection> data) {
        super.mContext = context;
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
            return CollectionViewType.COLLECTION_CARD;
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Collection item, int position) {
        ((CollectionViewHolder) viewHolder).bindDataToView(item, position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case CollectionViewType.COLLECTION_COMPAT:
                return new CollectionCompatViewHolder(inflateItemView(parent, R.layout.item_collection_compat), mContext, mIsShowUser);
            case CollectionViewType.COLLECTION_CARD:
                return new CollectionCardViewHolder(inflateItemView(parent, R.layout.item_collection_card), mContext, mIsShowUser);
        }

        return null;
    }

}
