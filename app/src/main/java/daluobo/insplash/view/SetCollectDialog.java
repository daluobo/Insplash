package daluobo.insplash.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.model.net.Photo;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.viewmodel.MeCollectionsViewModel;

/**
 * Created by daluobo on 2017/12/13.
 */

public class SetCollectDialog extends DialogFragment {
    public static final String ARG_PHOTO = "photo";
    private MeCollectionsViewModel mViewModel;
    private SelectCollectionAdapter mAdapter;

    @BindView(R.id.dismiss_btn)
    ImageView mDismissBtn;
    @BindView(R.id.title)
    EditText mTitle;
    @BindView(R.id.description)
    EditText mDescription;
    @BindView(R.id.isPrivate)
    CheckBox mIsPrivate;
    @BindView(R.id.new_collection_container)
    LinearLayout mNewCollectionContainer;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.cancel_btn)
    TextView mCancelBtn;
    @BindView(R.id.add_btn)
    TextView mAddBtn;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

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
        mAdapter.setOnCollectionClickListener(new SelectCollectionAdapter.OnCollectionClickListener() {
            @Override
            public void onCollectionClick(Collection collection, int position) {
                addToCollection(collection, position);
            }
        });
    }

    private void initView() {
        mDismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetCollectDialog.this.dismiss();
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelCreate();
            }
        });
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddBtn.getText().equals("Create")) {
                    String title = mTitle.getText().toString();
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
                } else {
                    mNewCollectionContainer.setVisibility(View.VISIBLE);
                    mCancelBtn.setVisibility(View.VISIBLE);

                    mListView.setVisibility(View.GONE);

                    mAddBtn.setText("Create");
                }

            }
        });
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.addItemDecoration(new LineDecoration(getContext(), RecyclerView.VERTICAL));
        mListView.setAdapter(mAdapter);

        loadCollections();
    }

    private void cancelCreate() {
        mTitle.setText("");
        mDescription.setText("");
        mIsPrivate.setChecked(false);
        mNewCollectionContainer.setVisibility(View.GONE);
        mCancelBtn.setVisibility(View.GONE);

        mListView.setVisibility(View.VISIBLE);

        mAddBtn.setText("Create New");
    }

    private void loadCollections() {
        mViewModel.onRefresh().observe(this, new ResourceObserver<Resource<List<Collection>>, List<Collection>>(getContext()) {
            @Override
            protected void onSuccess(List<Collection> collections) {
                mAdapter.clearItems();
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

                mAdapter.addItem(collection);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            protected void onFinal() {
                super.onFinal();

                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    public void addToCollection(Collection collection, final int position) {
        mViewModel.addToCollection(collection.id, mViewModel.getPhoto().id).observe(this, new ResourceObserver<Resource<Photo>, Photo>(getContext()) {
            @Override
            protected void onSuccess(Photo photo) {

            }

            @Override
            protected void onFinal() {
                super.onFinal();

                mAdapter.notifyItemChanged(position);
            }
        });
    }
}
