package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.model.Collection;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/14.
 */

public class SelectCollectionAdapter extends BaseRecyclerAdapter<Collection, SelectCollectionAdapter.ViewHolder> {
    Context mContext;

    public SelectCollectionAdapter(Context context, List<Collection> data) {
        this.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(ViewHolder viewHolder, Collection item, int position) {
        viewHolder.mTitle.setText(item.title);
        if (item.privateX) {
            ViewUtil.setDrawableEnd(viewHolder.mTitle, viewHolder.mIcLock);
        } else {
            ViewUtil.setDrawableEnd(viewHolder.mTitle, null);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_select_collection));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.title)
        TextView mTitle;

        @BindDrawable(R.drawable.ic_lock_outline)
        Drawable mIcLock;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
