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
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.adapter.SelectCollectionAdapter;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.model.Collection;
import daluobo.insplash.viewmodel.MeCollectionsViewModel;

/**
 * Created by daluobo on 2017/12/13.
 */

public class SetCollectDialog extends DialogFragment {
    private MeCollectionsViewModel mUserViewModel;
    private SelectCollectionAdapter mAdapter;

    @BindView(R.id.dismiss_btn)
    ImageView mDismissBtn;
    @BindView(R.id.new_collection)
    EditText mNewCollection;
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
        mUserViewModel = ViewModelProviders.of(this).get(MeCollectionsViewModel.class);

    }

    private void initView() {

        mAdapter = new SelectCollectionAdapter(getContext(), mUserViewModel.getData());

        mDismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetCollectDialog.this.dismiss();
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewCollectionContainer.setVisibility(View.GONE);
                mCancelBtn.setVisibility(View.GONE);

                mListView.setVisibility(View.VISIBLE);

                mAddBtn.setText("Create New");
            }
        });
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewCollectionContainer.setVisibility(View.VISIBLE);
                mCancelBtn.setVisibility(View.VISIBLE);

                mListView.setVisibility(View.GONE);

                mAddBtn.setText("Create");
            }
        });
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mListView.addItemDecoration(new LineDecoration(getContext(), RecyclerView.VERTICAL));
        mListView.setAdapter(mAdapter);

        mUserViewModel.onRefresh().observe(this, new ResourceObserver<Resource<List<Collection>>, List<Collection>>(getContext()) {
            @Override
            protected void onSuccess(List<Collection> collections) {
                mAdapter.addItems(collections);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}
