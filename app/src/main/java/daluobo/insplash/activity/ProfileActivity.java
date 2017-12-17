package daluobo.insplash.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import daluobo.insplash.R;
import daluobo.insplash.base.arch.Resource;
import daluobo.insplash.base.arch.ResourceObserver;
import daluobo.insplash.base.view.BaseActivity;
import daluobo.insplash.model.net.User;
import daluobo.insplash.viewmodel.UserViewModel;

public class ProfileActivity extends BaseActivity {
    public static final String ARG_USER = "user";
    protected UserViewModel mViewModel;

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.first_name)
    EditText mFirstName;
    @BindView(R.id.last_name)
    EditText mLastName;
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.url)
    EditText mUrl;
    @BindView(R.id.location)
    EditText mLocation;
    @BindView(R.id.bio)
    EditText mBio;
    @BindView(R.id.instagram_username)
    EditText mInstagramUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    public void initData() {
        User user = getIntent().getParcelableExtra(ARG_USER);
        mViewModel = new UserViewModel();

        mViewModel.setUser(user);
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                mUsername.setText(user.username);
                mFirstName.setText(user.first_name);
                mLastName.setText(user.last_name);
                mEmail.setText(user.email);

                mLocation.setText(user.location);
                mUrl.setText(user.portfolio_url);
                mBio.setText(user.bio);
                mInstagramUsername.setText(user.instagram_username);
            }
        });
    }

    @Override
    public void initView() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setText("Profile");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            Map<String, String> param = new HashMap<>();
            param.put("username", mUsername.getText().toString());
            param.put("first_name", mFirstName.getText().toString());
            param.put("last_name", mLastName.getText().toString());
            param.put("email", mEmail.getText().toString());

            param.put("location", mLocation.getText().toString());
            param.put("url", mUrl.getText().toString());
            param.put("bio", mBio.getText().toString());
            param.put("instagram_username", mInstagramUsername.getText().toString());

            mViewModel.updateProfile(param).observe(this, new ResourceObserver<Resource<User>, User>(this) {
                @Override
                protected void onSuccess(User user) {
                    finish();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

}
