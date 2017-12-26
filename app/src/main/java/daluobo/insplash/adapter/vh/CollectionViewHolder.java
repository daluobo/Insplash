package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/25.
 */

public abstract class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

    Context mContext;
    Collection mCollection;
    boolean mIsShowUser = true;
    int mContainerWidth;

    public CollectionViewHolder(View itemView, Context context, boolean isShowUser) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mContext = context;
        mIsShowUser = isShowUser;

        mContainer.setOnClickListener(this);

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

    public void bindDataToView(Collection collection, int posotion) {
        mCollection = collection;
        mTitle.setText(collection.title);

        if (collection.description != null) {
            mDescription.setText(collection.description);
            mDescription.setVisibility(View.VISIBLE);
        } else {
            mDescription.setVisibility(View.GONE);
        }

        if (mIsShowUser) {
            mUsername.setText(collection.user.name);
            ImgUtil.loadImg(mContext, mAvatar, collection.user.profile_image.small);

            mUsername.setVisibility(View.VISIBLE);
            mAvatar.setVisibility(View.VISIBLE);
        } else {
            mUsername.setVisibility(View.INVISIBLE);
            if (collection.privateX) {
                mAvatar.setVisibility(View.VISIBLE);
                mAvatar.setImageDrawable(mIcLock);
            } else {
                mAvatar.setVisibility(View.INVISIBLE);
            }
        }

        mTotalPhotos.setText(collection.total_photos + " Photos");
    }
}
