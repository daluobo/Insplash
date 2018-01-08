package daluobo.insplash.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.view.LoadableAdapter;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.User;
import daluobo.insplash.util.ImgUtil;

/**
 * Created by daluobo on 2017/12/7.
 */

public class UsersAdapter extends LoadableAdapter<User> {

    @IntDef({UserViewType.COMPAT, UserViewType.CARD})
    private @interface UserViewType {
        int COMPAT = 10;
        int CARD = 11;
    }

    public UsersAdapter(Context context, List<User> data) {
        super.mContext = context;
        super.mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowFooter && position == getItemCount() - 1) {
            return ItemViewType.FOOTER_TYPE;
        } else if (ConfigHelper.isCompatView()) {
            return UserViewType.COMPAT;
        } else {
            return UserViewType.CARD;
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, User item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.mUser = item;
        holder.mName.setText(item.name);
        holder.mUsername.setText("@ " + item.username);
        if (item.location != null) {
            holder.mLocation.setText(item.location);
            holder.mLocation.setVisibility(View.VISIBLE);
        } else {
            holder.mLocation.setVisibility(View.GONE);
        }
        ImgUtil.loadImg(mContext, holder.mAvatar, item.profile_image.large);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case UserViewType.COMPAT:
                return new ViewHolder(inflateItemView(parent, R.layout.item_user_compat));
            case UserViewType.CARD:
                return new ViewHolder(inflateItemView(parent, R.layout.item_user));
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.avatar)
        ImageView mAvatar;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.location)
        TextView mLocation;
        @BindView(R.id.container)
        RelativeLayout mContainer;

        User mUser;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            NavHelper.toUser(mContext, mUser, mAvatar);
        }
    }
}
