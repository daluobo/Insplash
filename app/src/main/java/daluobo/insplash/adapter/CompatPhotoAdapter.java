package daluobo.insplash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/23.
 */

public class CompatPhotoAdapter extends FooterAdapter<Photo> {
    protected Context mContext;

    public CompatPhotoAdapter(Context context, List<Photo> data) {
        this.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Photo item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mPhoto = item;

        int width = ViewUtil.getScreenSize(mContext)[0] - 3 * DimensionUtil.dpToPx(8);

        ViewGroup.LayoutParams lp = holder.mPhotoView.getLayoutParams();
        lp.width = width / 2;
        lp.height = lp.width * 3 / 2;
        holder.mPhotoView.setLayoutParams(lp);

        ImgUtil.loadImg(mContext, holder.mPhotoView, new ColorDrawable(Color.parseColor(item.color)), item.urls.small);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_photo_compat));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.photo_view)
        ImageView mPhotoView;

        Photo mPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mPhotoView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            NavHelper.toPhoto(mContext, mPhoto, mPhotoView);
        }
    }
}
