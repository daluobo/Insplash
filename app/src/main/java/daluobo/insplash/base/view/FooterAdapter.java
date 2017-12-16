package daluobo.insplash.base.view;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import daluobo.insplash.R;

/**
 * Created by daluobo on 2017/11/24.
 */

public abstract class FooterAdapter<D> extends BaseRecyclerAdapter<D, RecyclerView.ViewHolder> {
    protected boolean isShowFooter = true;

    @IntDef({ItemViewType.NORMAL_TYPE, ItemViewType.FOOTER_TYPE})
    public @interface ItemViewType {
        int NORMAL_TYPE = 0;
        int FOOTER_TYPE = 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowFooter && position >= 10 && position == getItemCount() - 1) {
            return ItemViewType.FOOTER_TYPE;
        } else {
            return ItemViewType.NORMAL_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemViewType.FOOTER_TYPE) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final D item = getItem(position);

        switch (getItemViewType(position)) {
            case ItemViewType.NORMAL_TYPE:
                bindDataToItemView(viewHolder, item, position);
                break;
            case ItemViewType.FOOTER_TYPE:
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mData.size() == 0) {
            return 0;
        }
        if (mData.size() > 0 && mData.size() % 10 != 0) {
            isShowFooter = false;
            return mData.size();
        } else if (!isShowFooter) {
            return mData.size();
        } else {
            isShowFooter = true;
            return mData.size() + 1;
        }
    }

    public void setShowFooter(boolean showFooter) {
        isShowFooter = showFooter;
        notifyDataSetChanged();
    }

    protected ProcessBarViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return new ProcessBarViewHolder(inflateItemView(parent, R.layout.item_processbar));
    }

    public class ProcessBarViewHolder extends RecyclerView.ViewHolder {
        public ProcessBarViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);
}
