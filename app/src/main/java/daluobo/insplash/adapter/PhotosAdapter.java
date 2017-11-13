package daluobo.insplash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.BaseRecyclerAdapter;
import daluobo.insplash.model.Photo;
import daluobo.insplash.util.ImgUtil;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotosAdapter extends BaseRecyclerAdapter<Photo, PhotosAdapter.ViewHolder> {
    Context mContext;

    public PhotosAdapter(Context context, List<Photo> data) {
        this.mContext = context;
        super.mData = data;
    }

    @Override
    protected void bindDataToItemView(ViewHolder holder, Photo item, int position) {
        holder.mUserName.setText(item.user.name);
        ImgUtil.loadImg(mContext, holder.mImg, item.urls.small);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_photo));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.user_name)
        TextView mUserName;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
