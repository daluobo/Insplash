package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;

/**
 * Created by daluobo on 2017/12/25.
 */

public class CollectionCompatViewHolder extends CollectionViewHolder{
    @BindView(R.id.container)
    RelativeLayout mContainer;

    public CollectionCompatViewHolder(View itemView, Context context, boolean isShowUser) {
        super(itemView, context);
        ButterKnife.bind(this, itemView);

        mContainer.setOnClickListener(this);
    }

}
