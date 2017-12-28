package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/25.
 */

public class CoverViewHolder extends CollectionViewHolder {
    @BindView(R.id.cover_photo)
    ImageView mCoverPhoto;

    public CoverViewHolder(View itemView, Context context, boolean isShowUser) {
        super(itemView, context, isShowUser);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindDataToView(Collection collection, int position) {
        ViewGroup.LayoutParams lp = mCoverPhoto.getLayoutParams();
        lp.width = mContainerWidth;
        lp.height = lp.width * 2 / 3;
        mCoverPhoto.setLayoutParams(lp);
        if (collection.cover_photo != null) {
            ImgUtil.loadImg(mContext, mCoverPhoto, ViewUtil.createColorDrawable(collection.cover_photo.color), collection.cover_photo.urls.small);
        } else {
            mCoverPhoto.setImageDrawable(mIcNeutral);
        }

        super.bindDataToView(collection, position);
    }

    @Override
    public void onClick(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];

        NavHelper.toCollection(mContext, mCollection, x + view.getWidth() / 2, y + mCoverPhoto.getHeight() / 2);
    }
}
