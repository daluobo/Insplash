package daluobo.insplash.activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.common.AppConstant;
import daluobo.insplash.model.Token;
import daluobo.insplash.util.ToastUtil;
import daluobo.insplash.viewmodel.OauthViewModel;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_btn)
    Button mLoginBtn;

    private OauthViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null
                && intent.getData() != null
                && !TextUtils.isEmpty(intent.getData().getAuthority())
                && AppConstant.APP_CALLBACK_URL.equals(intent.getData().getAuthority())) {

            mViewModel.getToken(intent.getData().getQueryParameter("code")).observe(this, new Observer<Resource<Token>>() {

                @Override
                public void onChanged(@Nullable Resource<Token> resource) {
                    switch (resource.status) {
                        case SUCCESS:
                            ToastUtil.showShort(LoginActivity.this, "token: " + resource.data.access_token);
                            break;
                        case ERROR:
                            ToastUtil.showShort(LoginActivity.this, resource.message);
                            break;
                        case LOADING:
                            ToastUtil.showShort(LoginActivity.this, "loading");
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void initData() {
        mViewModel = new OauthViewModel();
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstant.getLoginUrl())));
    }


}
