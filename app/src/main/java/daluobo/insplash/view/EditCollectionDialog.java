package daluobo.insplash.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.model.net.Collection;

/**
 * Created by daluobo on 2017/12/20.
 */

public class EditCollectionDialog extends DialogFragment {
    public static final String ARG_COLLECTION = "collection";

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

                } else {

                }
                break;
        }
    }

}
