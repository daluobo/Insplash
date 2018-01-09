package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2017/12/25.
 */

public class CollectionCardViewHolder extends CollectionViewHolder {
    @BindView(R.id.container)
    CardView mContainer;
    @BindView(R.id.username)
    TextView mUsername;


    public CollectionCardViewHolder(View itemView, Context context, boolean isShowUser) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);

        mIsShowUser = isShowUser;

        mContainer.setOnClickListener(this);

        mUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHelper.toUser(mContext, mCollection.user, mAvatar);
            }
        });

    }

    @Override
    public void bindDataToView(Collection collection, int position) {
        super.bindDataToView(collection, position);


        if (mIsShowUser) {
            mUsername.setVisibility(View.VISIBLE);
            mUsername.setText(collection.user.name);
        } else {
            mUsername.setVisibility(View.GONE);
        }
    }
}
