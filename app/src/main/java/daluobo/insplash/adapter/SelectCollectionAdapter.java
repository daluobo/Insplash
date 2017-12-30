package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/14.
 */

public class SelectCollectionAdapter extends BaseRecyclerAdapter<Collection, SelectCollectionAdapter.ViewHolder> {
    OnCollectionClickListener mOnCollectionClickListener;
    List<Collection> mCurrentUserCollections;


    public void setCurrentUserCollections(List<Collection> currentUserCollections) {
        mCurrentUserCollections = currentUserCollections;
    }

    public SelectCollectionAdapter(Context context, List<Collection> data) {
        super.mContext = context;
        data.add(0, new Collection());
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(ViewHolder viewHolder, Collection item, int position) {
        viewHolder.mCollection = item;
        viewHolder.mPosition = position;

        if (position == 0) {
            viewHolder.mContentContainer.setVisibility(View.GONE);
            viewHolder.mPreview.setVisibility(View.GONE);

            viewHolder.mCreateHint.setVisibility(View.VISIBLE);
            return;
        } else {
            viewHolder.mContentContainer.setVisibility(View.VISIBLE);
            viewHolder.mPreview.setVisibility(View.VISIBLE);

            viewHolder.mCreateHint.setVisibility(View.GONE);
        }

        viewHolder.mTitle.setText(item.title);
        viewHolder.mTotalPhotos.setText(item.total_photos + " Photos");
        viewHolder.mProgressBar.setVisibility(View.GONE);

        if (item.privateX) {
            ViewUtil.setDrawableStart(viewHolder.mTitle, viewHolder.mIcLock);
        } else {
            ViewUtil.setDrawableStart(viewHolder.mTitle, null);
        }

        if (item.cover_photo != null) {
            ImgUtil.loadImg(mContext, viewHolder.mPreview, item.cover_photo.urls.regular);
        } else {
            viewHolder.mPreview.setImageDrawable(null);
        }

        if (mCurrentUserCollections.contains(item)) {
            viewHolder.mHint.setBackground(viewHolder.mIcDone);
        } else {
            viewHolder.mHint.setBackground(viewHolder.mIcAdd);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_select_collection));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.create_hint)
        TextView mCreateHint;
        @BindView(R.id.preview)
        ImageView mPreview;
        @BindView(R.id.total_photos)
        TextView mTotalPhotos;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.hint)
        ImageView mHint;
        @BindView(R.id.progress_bar)
        ProgressBar mProgressBar;
        @BindView(R.id.content_container)
        RelativeLayout mContentContainer;
        @BindView(R.id.container)
        CardView mContainer;

        @BindDrawable(R.drawable.ic_lock_small)
        Drawable mIcLock;
        @BindDrawable(R.drawable.ic_add)
        Drawable mIcAdd;
        @BindDrawable(R.drawable.ic_done)
        Drawable mIcDone;
        @BindColor(R.color.colorWhite)
        int mColorWhite;

        Collection mCollection;
        int mPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mIcLock = ViewUtil.tintDrawable(mIcLock, mColorWhite);

            mContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mPosition == 0) {
                mOnCollectionClickListener.onCreateClick();
                return;
            }

            mHint.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            if (mCurrentUserCollections.contains(mCollection)) {
                mOnCollectionClickListener.onRemoveFromCollectionClick(mCollection, mPosition);
            } else {
                mOnCollectionClickListener.onAddToCollectionClick(mCollection, mPosition);
            }
        }
    }

    public interface OnCollectionClickListener {
        void onCreateClick();

        void onAddToCollectionClick(Collection collection, int position);

        void onRemoveFromCollectionClick(Collection collection, int position);
    }

    public void setOnCollectionClickListener(OnCollectionClickListener onCollectionClickListener) {
        mOnCollectionClickListener = onCollectionClickListener;
    }

}
