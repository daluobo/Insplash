package daluobo.insplash.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.model.net.Collection;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.viewmodel.CollectionsEditViewModel;

/**
 * Created by daluobo on 2017/12/20.
 */

public class EditCollectionDialog extends DialogFragment {
    public static final String ARG_COLLECTION = "collection";
    protected CollectionsEditViewModel mViewModel;

    @BindColor(R.color.colorPrimary)
    int mColorPrimary;
    @BindColor(R.color.colorAccent)
    int mColorAccent;
    @BindColor(R.color.colorWhite)
    int mColorWhite;

    @BindView(R.id.dialog_title)
    TextView mDialogTitle;
    @BindView(R.id.dismiss_btn)
    ImageView mDismissBtn;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.description)
    EditText mDescription;
    @BindView(R.id.isPrivate)
    CheckBox mIsPrivate;
    @BindView(R.id.delete_btn)
    TextView mDeleteBtn;
    @BindView(R.id.save_btn)
    TextView mSaveBtn;
    @BindView(R.id.hint)
    TextView mHint;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_collection, container);
        ButterKnife.bind(this, view);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        initData();
        initView();
        return view;
    }

    private void initData() {
        Collection collection = getArguments().getParcelable(ARG_COLLECTION);

        mViewModel = ViewModelProviders.of(this).get(CollectionsEditViewModel.class);
        mViewModel.setCollection(collection);
        mViewModel.getCollection().observe(this, new Observer<Collection>() {
            @Override
            public void onChanged(@Nullable Collection collection) {
                mName.setText(collection.title);
                mDescription.setText(collection.description);
                mIsPrivate.setChecked(collection.privateX);
            }
        });
    }

    private void initView() {

    }

    @OnClick({R.id.dismiss_btn, R.id.delete_btn, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dismiss_btn:
                this.dismiss();
                break;
            case R.id.delete_btn:
                if (mHint.getVisibility() == View.VISIBLE) {
                    mHint.setVisibility(View.GONE);

                    mDeleteBtn.setText("Delete Collection");
                    mDeleteBtn.setTextColor(mColorAccent);

                    mSaveBtn.setText("Save");
                    mSaveBtn.setTextColor(mColorPrimary);
                    mSaveBtn.setBackgroundColor(mColorWhite);
                } else {
                    mHint.setVisibility(View.VISIBLE);

                    mDeleteBtn.setText("Cancel");
                    mDeleteBtn.setTextColor(mColorPrimary);

                    mSaveBtn.setText("Delete");
                    mSaveBtn.setTextColor(mColorWhite);
                    mSaveBtn.setBackgroundColor(mColorAccent);
                }
                break;
            case R.id.save_btn:
                if (mHint.getVisibility() == View.VISIBLE) {
                    mViewModel.deleteCollection(mViewModel.getCollectionData().id).observe(this, new ResourceObserver<Resource<Object>, Object>(getContext()) {
                        @Override
                        protected void onSuccess(Object o) {
                            ToastUtil.showShort(getContext(), "Delete success!");
                            EditCollectionDialog.this.dismiss();
                        }
                    });
                } else {
                    if (mName.getText().length() == 0) {
                        ToastUtil.showShort(getContext(), "Name must not empty");
                        return;
                    }

                    Map<String, Object> param = new HashMap<>();
                    param.put("title", mName.getText());
                    param.put("description", mDescription.getText());
                    param.put("private", mIsPrivate.isChecked());

                    mViewModel.updateCollection(mViewModel.getCollectionData().id, param).observe(this, new ResourceObserver<Resource<Collection>, Collection>(getContext()) {
                        @Override
                        protected void onSuccess(Collection collection) {
                            ToastUtil.showShort(getContext(), "Update success!");
                            EditCollectionDialog.this.dismiss();
                        }
                    });
                }
                break;
        }
    }

}
