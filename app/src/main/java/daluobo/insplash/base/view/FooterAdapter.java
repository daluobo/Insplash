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

    private static final int NORMAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;

    @IntDef({NORMAL_TYPE, FOOTER_TYPE})
    private @interface ViewType {
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOTER_TYPE;
        } else {
            return NORMAL_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case FOOTER_TYPE:
                return onCreateFooterViewHolder(parent, viewType);
            case NORMAL_TYPE:
                return onCreateItemViewHolder(parent, viewType);
        }
        return onCreateItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final D item = getItem(position);

        switch (getItemViewType(position)) {
            case NORMAL_TYPE:
                bindDataToItemView(viewHolder, item, position);
                break;
            case FOOTER_TYPE:
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mData.size() > 0 && isShowFooter) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    public void setShowFooter(boolean showFooter) {
        isShowFooter = showFooter;
        notifyDataSetChanged();
    }

    ProcessBarViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return new ProcessBarViewHolder(inflateItemView(parent, R.layout.item_processbar));
    }

    public class ProcessBarViewHolder extends RecyclerView.ViewHolder {
        public ProcessBarViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);
}
