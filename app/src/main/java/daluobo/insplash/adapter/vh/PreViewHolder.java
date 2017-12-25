package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import daluobo.insplash.R;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.util.ImgUtil;

/**
 * Created by daluobo on 2017/12/25.
 */

public class PreViewHolder extends CollectionViewHolder {
    @BindView(R.id.preview_container)
    LinearLayout mPreviewContainer;
    @BindView(R.id.preview_0)
    ImageView mPreview0;
    @BindView(R.id.preview_1)
    ImageView mPreview1;
    @BindView(R.id.preview_2)
    ImageView mPreview2;

    public PreViewHolder(View itemView, Context context, boolean isShowUser) {
        super(itemView, context, isShowUser);
    }

    @Override
    public void bindDataToView(Collection collection, int position) {
        ViewGroup.LayoutParams containerLp = mPreviewContainer.getLayoutParams();

        int width = mContainerWidth / 3;

        containerLp.height = width;
        mPreviewContainer.setLayoutParams(containerLp);

        ColorDrawable bg = new ColorDrawable(Color.parseColor(collection.cover_photo.color));

        ImgUtil.loadImgCC(mContext, mPreview0, bg, collection.preview_photos.get(0).urls.thumb);
        ImgUtil.loadImgCC(mContext, mPreview1, bg, collection.preview_photos.get(1).urls.thumb);
        ImgUtil.loadImgCC(mContext, mPreview2, bg, collection.preview_photos.get(2).urls.thumb);

        super.bindDataToView(collection, position);
    }

    @Override
    public void onClick(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];

        NavHelper.toCollection(mContext, mCollection, x + view.getWidth() / 2, y + mPreview0.getHeight() / 2);
    }
}
