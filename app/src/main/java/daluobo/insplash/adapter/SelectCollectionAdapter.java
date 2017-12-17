package daluobo.insplash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2017/12/14.
 */

public class SelectCollectionAdapter extends BaseRecyclerAdapter<Collection, SelectCollectionAdapter.ViewHolder> {
    Context mContext;
    OnCollectionClickListener mOnCollectionClickListener;

    public SelectCollectionAdapter(Context context, List<Collection> data) {
        this.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(ViewHolder viewHolder, Collection item, int position) {
        viewHolder.mCollection = item;
        viewHolder.mPosition = position;
        viewHolder.mTitle.setText(item.title);
        viewHolder.mTotalPhotos.setText(item.total_photos + " Photos");
        viewHolder.mProgressBar.setVisibility(View.GONE);

        if (item.privateX) {
            viewHolder.mIsPrivate.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mIsPrivate.setVisibility(View.GONE);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_select_collection));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.total_photos)
        TextView mTotalPhotos;
        @BindView(R.id.is_private)
        ImageView mIsPrivate;
        @BindView(R.id.progress_bar)
        ProgressBar mProgressBar;
        @BindView(R.id.container)
        RelativeLayout mContainer;

        Collection mCollection;
        int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnCollectionClickListener.onCollectionClick(mCollection, mPosition);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }
    public interface OnCollectionClickListener{
        void onCollectionClick(Collection collection, int position);
    }
    public void setOnCollectionClickListener(OnCollectionClickListener onCollectionClickListener) {
        mOnCollectionClickListener = onCollectionClickListener;
    }

}
