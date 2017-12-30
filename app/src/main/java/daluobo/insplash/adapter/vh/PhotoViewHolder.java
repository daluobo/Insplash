package daluobo.insplash.adapter.vh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.util.ViewUtil;

/**
 * Created by daluobo on 2017/12/29.
 */

public abstract class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.photo_view)
    public ImageView mPhotoView;
    @BindView(R.id.progress_bar)
    public ProgressBar mProgressBar;
    @BindView(R.id.like_btn)
    public ImageView mLikeBtn;
    @BindView(R.id.likes)
    public TextSwitcher mLikes;

    @BindColor(R.color.colorAccent)
    public int mColorAccent;
    @BindColor(R.color.colorTitle)
    public int mColorTitle;
    @BindDrawable(R.drawable.ic_favorite_border)
    public Drawable mIcFavoriteBorder;
    @BindDrawable(R.drawable.ic_favorite)
    public Drawable mIcFavorite;

    @BindString(R.string.msg_please_login)
    public String mMsgPleaseLogin;

    TextView mLikeText;

    OnActionClickListener mOnActionClickListener;
    Context mContext;
    Photo mPhoto;
    int mPosition;

    public PhotoViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mContext = context;
        mIcFavorite = ViewUtil.tintDrawable(mIcFavorite, mColorAccent);

        mPhotoView.setOnClickListener(this);

        mLikes.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_form_bottom));
        mLikes.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_to_top));
        mLikes.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                mLikeText = new TextView(mContext);
                mLikeText.setLayoutParams(new TextSwitcher.LayoutParams(TextSwitcher.LayoutParams.MATCH_PARENT, TextSwitcher.LayoutParams.MATCH_PARENT));
                mLikeText.setGravity(Gravity.CENTER);
                mLikeText.setBackgroundColor(Color.TRANSPARENT);
                mLikeText.setTextColor(mColorTitle);
                return mLikeText;
            }
        });
    }
}
