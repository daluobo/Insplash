package daluobo.insplash.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import daluobo.insplash.R;
import daluobo.insplash.adapter.vh.CompatPhotoViewHolder;
import daluobo.insplash.adapter.vh.OnActionClickListener;
import daluobo.insplash.adapter.vh.PhotoViewHolder;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.FooterAdapter;
import daluobo.insplash.helper.ConfigHelper;
import daluobo.insplash.helper.NavHelper;
import daluobo.insplash.model.net.LikePhoto;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.viewmodel.PhotoViewModel;

/**
 * Created by daluobo on 2017/11/12.
 */

public class PhotosAdapter extends FooterAdapter<Photo> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected boolean mIsShowUser = true;
    protected int mColumn = 1;

    protected PhotoViewModel mViewModel;
    protected LifecycleOwner mLifecycleOwner;
    protected FragmentManager mFragmentManager;

    @IntDef({PhotoViewType.COMPAT, PhotoViewType.PREVIEW})
    private @interface PhotoViewType {
        int COMPAT = 10;
        int PREVIEW = 11;
    }

    public PhotosAdapter(Context context, List<Photo> data, LifecycleOwner owner, PhotoViewModel viewModel, FragmentManager manager) {
        this.mContext = context;
        super.mData = data;
        this.mLifecycleOwner = owner;
        this.mViewModel = viewModel;
        this.mFragmentManager = manager;

        mInflater = LayoutInflater.from(mContext);
    }

    public PhotosAdapter(Context context, List<Photo> data, LifecycleOwner owner, PhotoViewModel viewModel, FragmentManager manager, boolean isShowUser, int column) {
        this(context, data, owner, viewModel, manager);
        this.mIsShowUser = isShowUser;
        this.mColumn = column;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowFooter && position == getItemCount() - 1) {
            return ItemViewType.FOOTER_TYPE;
        } else if (ConfigHelper.isCompatView()) {
            return PhotoViewType.COMPAT;
        } else {
            return PhotoViewType.PREVIEW;
        }
    }

    @Override
    protected void bindDataToItemView(RecyclerView.ViewHolder viewHolder, Photo item, int position) {
        if (viewHolder instanceof PhotoViewHolder) {
            PhotoViewHolder pvh = (PhotoViewHolder) viewHolder;
            pvh.bindDataToView(item, position);
        } else if (viewHolder instanceof CompatPhotoViewHolder) {
            CompatPhotoViewHolder cpv = (CompatPhotoViewHolder) viewHolder;
            cpv.bindDataToView(item, position);
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PhotoViewType.COMPAT:
                return new CompatPhotoViewHolder(inflateItemView(parent, R.layout.item_photo_compat), mContext, mColumn, new OnActionClickListener() {
                    @Override
                    public void onLikeClick(Photo photo) {
                        mViewModel.likePhoto(photo).observe(mLifecycleOwner, new ResourceObserver<Resource<LikePhoto>, LikePhoto>(mContext) {
                            @Override
                            protected void onSuccess(LikePhoto likePhoto) {
                                onPhotoLikeChange(likePhoto.photo);
                            }
                        });
                    }

                    @Override
                    public void onDownloadClick(Photo photo) {

                    }

                    @Override
                    public void onCollectClick(Photo photo) {

                    }

                });
            case PhotoViewType.PREVIEW:
                return new PhotoViewHolder(inflateItemView(parent, R.layout.item_photo), mContext, mIsShowUser, new OnActionClickListener() {
                    @Override
                    public void onLikeClick(Photo photo) {
                        mViewModel.likePhoto(photo).observe(mLifecycleOwner, new ResourceObserver<Resource<LikePhoto>, LikePhoto>(mContext) {
                            @Override
                            protected void onSuccess(LikePhoto likePhoto) {
                                onPhotoLikeChange(likePhoto.photo);
                            }
                        });
                    }

                    @Override
                    public void onDownloadClick(Photo photo) {

                    }

                    @Override
                    public void onCollectClick(Photo photo) {
                        NavHelper.collectPhoto(mFragmentManager, photo);
                    }
                });
        }

        return null;
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

}
