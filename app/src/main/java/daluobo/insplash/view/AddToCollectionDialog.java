package daluobo.insplash.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.SelectCollectionAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.model.net.CollectPhoto;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.viewmodel.MeCollectionsViewModel;

/**
 * Created by daluobo on 2017/12/13.
 */

public class AddToCollectionDialog extends DialogFragment {
    public static final String ARG_PHOTO = "photo";
    private MeCollectionsViewModel mViewModel;
    private SelectCollectionAdapter mAdapter;
    private int dialogWidth = 0;

    @BindView(R.id.add_title)
    TextView mAddTitle;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.add_container)
    RelativeLayout mAddContainer;
    @BindView(R.id.create_title)
    TextView mCreateTitle;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.description)
    EditText mDescription;
    @BindView(R.id.isPrivate)
    CheckBox mIsPrivate;
    @BindView(R.id.create_content_container)
    LinearLayout mCreateContentContainer;
    @BindView(R.id.cancel_btn)
    TextView mCancelBtn;
    @BindView(R.id.create_btn)
    TextView mCreateBtn;
    @BindView(R.id.create_container)
    RelativeLayout mCreateContainer;
    @BindView(R.id.dismiss_btn)
    ImageView mDismissBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_collect, container);
        ButterKnife.bind(this, view);

        initData();
        initView();
        return view;
    }

    private void initData() {
        mViewModel = ViewModelProviders.of(this).get(MeCollectionsViewModel.class);
        Photo photo = getArguments().getParcelable(ARG_PHOTO);
        mViewModel.setPhoto(photo);

        mAdapter = new SelectCollectionAdapter(getContext(), mViewModel.getData());
        mAdapter.setCurrentUserCollections(photo.current_user_collections);
        mAdapter.setOnCollectionClickListener(new SelectCollectionAdapter.OnCollectionClickListener() {
            @Override
            public void onCreateClick() {
                mCreateContainer.animate().translationXBy(-dialogWidth).setDuration(300).start();
            }

            @Override
            public void onAddToCollectionClick(Collection collection, int position) {
                addToCollection(collection, position);
            }

            @Override
            public void onRemoveFromCollectionClick(Collection collection, int position) {
                removeFromCollection(collection, position);
            }
        });
    }

    private void initView() {
        mAddContainer.getViewTreeObserver().
                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mAddContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        dialogWidth = mAddContainer.getMeasuredWidth();

                        mCreateContainer.setTranslationX(dialogWidth);
                    }
                });

        mDismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCollectionDialog.this.dismiss();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCreate();
            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mName.getText().toString();
                if (title.length() == 0) {
                    ToastUtil.showShort(getContext(), "Name must not empty");
                    return;
                }
                String description = mDescription.getText().toString();
                Boolean isPrivate = mIsPrivate.isChecked();

                Map<String, Object> param = new HashMap<>();
                param.put("title", title);
                param.put("description", description);
                param.put("private", isPrivate);

                createCollection(param);

            }
        });
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.setAdapter(mAdapter);

        loadCollections();
    }

    private void cancelCreate() {
        mCreateContainer.animate().translationXBy(dialogWidth).setDuration(300).start();
    }

    private void loadCollections() {
        mViewModel.getMyCollections().observe(this, new ResourceObserver<Resource<List<Collection>>, List<Collection>>(getContext()) {
            @Override
            protected void onSuccess(List<Collection> collections) {
                mAdapter.clearItems();
                mAdapter.addItem(new Collection());
                mAdapter.addItems(collections);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFinal() {
                super.onFinal();

                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void createCollection(Map<String, Object> param) {
        mProgressBar.setVisibility(View.VISIBLE);
        mViewModel.createCollection(param).observe(this, new ResourceObserver<Resource<Collection>, Collection>(getContext()) {
            @Override
            protected void onSuccess(Collection collection) {
                cancelCreate();

                mAdapter.putItem(collection, 1);
                mAdapter.notifyItemInserted(1);
            }

            @Override
            protected void onFinal() {
                super.onFinal();

                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    public void addToCollection(final Collection collection, final int position) {
        mViewModel.addToCollection(collection.id, mViewModel.getPhoto().id).observe(this, new ResourceObserver<Resource<CollectPhoto>, CollectPhoto>(getContext()) {
            @Override
            protected void onSuccess(CollectPhoto collectPhoto) {
                collection.cover_photo = collectPhoto.collection.cover_photo;
                collection.total_photos = collectPhoto.collection.total_photos;

                mAdapter.setCurrentUserCollections(collectPhoto.photo.current_user_collections);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFinal() {
                super.onFinal();

                mAdapter.notifyItemChanged(position);
            }
        });
    }

    public void removeFromCollection(final Collection collection, final int position) {
        mViewModel.removeFromCollection(collection.id, mViewModel.getPhoto().id).observe(this, new ResourceObserver<Resource<CollectPhoto>, CollectPhoto>(getContext()) {
            @Override
            protected void onSuccess(CollectPhoto collectPhoto) {
                collection.cover_photo = collectPhoto.collection.cover_photo;
                collection.total_photos = collectPhoto.collection.total_photos;

                mAdapter.setCurrentUserCollections(collectPhoto.photo.current_user_collections);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFinal() {
                super.onFinal();

                mAdapter.notifyItemChanged(position);
            }
        });
    }
}
