package daluobo.insplash.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.LikePhoto;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.util.DimensionUtil;
import daluobo.insplash.util.ImgUtil;
import daluobo.insplash.util.ViewUtil;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotosAdapter extends FooterAdapter<Photo> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected boolean mIsShowUser = true;
    protected PhotoViewModel mViewModel;
    protected LifecycleOwner mLifecycleOwner;
    protected FragmentManager mFragmentManager;

    private PopupWindow mPopupWindow;
    private int[] mPopupWindowSize;
    private TextView mDownloadBtn;
    private TextView mCollectBtn;
    private int mPopupWindowPhotoPosition = -1;

    public PhotosAdapter(Context context, List<Photo> data, LifecycleOwner owner, PhotoViewModel viewModel, FragmentManager manager) {
        this.mContext = context;
        super.mData = data;
        this.mLifecycleOwner = owner;
        this.mViewModel = viewModel;
        this.mFragmentManager = manager;

        mInflater = LayoutInflater.from(mContext);
    }

    public PhotosAdapter(Context context, List<Photo> data, LifecycleOwner owner, PhotoViewModel viewModel, FragmentManager manager, boolean isShowUser) {
        this(context, data, owner, viewModel, manager);
        this.mIsShowUser = isShowUser;
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Photo item, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mPosition = position;

        if (item == null) {
            return;
        }
        holder.mPhoto = item;
        ColorDrawable bg = new ColorDrawable(Color.parseColor(item.color));

        ViewGroup.LayoutParams lp = holder.mPhotoView.getLayoutParams();
        lp.width = holder.mContainerWidth;
        lp.height = lp.width * item.height / item.width;
        holder.mPhotoView.setLayoutParams(lp);
        ImgUtil.loadImg(mContext, holder.mPhotoView, bg, item.urls.small);

        if (item.description != null) {
            holder.mDescription.setText(item.description);
            holder.mDescription.setVisibility(View.VISIBLE);
        } else {
            holder.mDescription.setVisibility(View.GONE);
        }

        if (mIsShowUser) {
            holder.mUsername.setText(item.user.name);
            ImgUtil.loadImg(mContext, holder.mAvatar, bg, item.user.profile_image.small);
            holder.mUsername.setVisibility(View.VISIBLE);
            holder.mAvatar.setVisibility(View.VISIBLE);
        } else {
            holder.mUsername.setVisibility(View.GONE);
            holder.mAvatar.setVisibility(View.GONE);
        }

        holder.mLikeBtn.setVisibility(View.VISIBLE);
        holder.mProgressBar.setVisibility(View.GONE);
        if (item.liked_by_user) {
            holder.mLikeBtn.setImageDrawable(holder.mIcFavorite);
        } else {
            holder.mLikeBtn.setImageDrawable(holder.mIcFavoriteBorder);
        }
        holder.mLikes.setCurrentText(item.likes + "");
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflateItemView(parent, R.layout.item_photo));
    }

    public void onPhotoLikeChange(Photo photo) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).id.equals(photo.id)) {
                mData.get(i).liked_by_user = photo.liked_by_user;
                mData.get(i).likes = photo.likes;
                notifyItemChanged(i);
                break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.photo_view)
        ImageView mPhotoView;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.avatar)
        ImageView mAvatar;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.like_btn)
        ImageView mLikeBtn;
        @BindView(R.id.likes)
        TextSwitcher mLikes;
        @BindView(R.id.more)
        ImageView mMore;
        @BindView(R.id.container)
        CardView mContainer;
        @BindView(R.id.progress_bar)
        ProgressBar mProgressBar;

        TextView mLikeText;

        int mPosition;
        Photo mPhoto;
        int mContainerWidth;

        @BindDrawable(R.drawable.ic_favorite_border_primary)
        Drawable mIcFavoriteBorder;
        @BindDrawable(R.drawable.ic_favorite)
        Drawable mIcFavorite;
        @BindColor(R.color.colorPrimary)
        int mColorPrimary;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContainerWidth = ViewUtil.getScreenSize(mContext)[0] - DimensionUtil.dip2px(mContext, 8);
            mPhotoView.setOnClickListener(this);

            mAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHelper.toUser(mContext, mPhoto.user, mAvatar);
                }
            });

            mUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHelper.toUser(mContext, mPhoto.user, mAvatar);
                }
            });

            mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMoreDialog(v, mPosition);
                }
            });

            mLikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLikeBtn.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);

                    onLikeClick(mPhoto);
                }
            });

            mLikes.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    mLikeText = new TextView(mContext);
                    mLikeText.setLayoutParams(new TextSwitcher.LayoutParams(TextSwitcher.LayoutParams.MATCH_PARENT, TextSwitcher.LayoutParams.MATCH_PARENT));
                    mLikeText.setGravity(Gravity.CENTER);
                    mLikeText.setBackgroundColor(Color.TRANSPARENT);
                    mLikeText.setTextColor(mColorPrimary);
                    return mLikeText;
                }
            });

        }

        @Override
        public void onClick(View v) {
            NavHelper.toPhoto(mContext, mPhoto, mPhotoView);
        }
    }

    private void showMoreDialog(View view, final int position) {
        if (mPopupWindow == null) {
            initPopupWindow();
        }
        mPopupWindowPhotoPosition = position;

        int[] location = new int[2];
        view.getLocationInWindow(location);

        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - mPopupWindowSize[0], location[1] - mPopupWindowSize[1]);
    }

    private void initPopupWindow() {
        final View contentView = mInflater.inflate(R.layout.menu_view_photo, null, false);
        mPopupWindowSize = ViewUtil.getViewSize(contentView);

        mDownloadBtn = contentView.findViewById(R.id.download_tv);
        mCollectBtn = contentView.findViewById(R.id.collect_tv);

        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownloadClick();
            }
        });

        mCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCollectClick(mData.get(mPopupWindowPhotoPosition));
            }
        });

        mPopupWindow = new PopupWindow(contentView,
                mPopupWindowSize[0],
                mPopupWindowSize[1],
                true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.ScaleAnimation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPopupWindow.setElevation(10);
        }
    }

    public void onLikeClick(Photo photo) {
        mViewModel.likePhoto(photo).observe(mLifecycleOwner, new ResourceObserver<Resource<LikePhoto>, LikePhoto>(mContext) {
            @Override
            protected void onSuccess(LikePhoto likePhoto) {
                onPhotoLikeChange(likePhoto.photo);
            }
        });
    }

    public void onDownloadClick() {

    }

    public void onCollectClick(Photo photo) {
        mPopupWindow.dismiss();
        NavHelper.collectPhoto(mFragmentManager, photo);
    }

}
